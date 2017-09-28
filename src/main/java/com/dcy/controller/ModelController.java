package com.dcy.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dcy.utils.Common;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * Created by Administrator on 2017/9/22.
 */
/**
 * 流程模型控制器
 *
 * @author henryyan
 */
@Controller
@RequestMapping(value = "${adminPath}/workflow/model")
public class ModelController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;

    @RequiresPermissions("sys:model:search")
    @RequestMapping(method = RequestMethod.GET,value = "/list",name = "流程模型列表")
    public String modelIndex(HttpServletRequest request) {
        return "/workflow/model-list";
    }

    /**
     * 模型列表
     */
    @RequiresPermissions("sys:model:search")
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "/getModelList",name = "流程模型数据")
    public Map modelList() {
        Map map = new HashMap();
        try {
            Integer count = (int) repositoryService.createModelQuery().count();
            if (count > 0){
                map = new Common().getBSTData(count, repositoryService.createModelQuery().list());
            }else {
                map = new Common().getBSTData(count, new ArrayList());
            }
        }catch (Exception ex){
            logger.error("modelList-=-"+ex.toString());
        }
        return map;
    }

    @RequiresPermissions("sys:model:add")
    @RequestMapping(method = RequestMethod.GET,value = "/add",name = "流程添加页面")
    public String add(HttpServletRequest request) {
        return "/workflow/templateAdd";
    }

    /**
     * 创建模型
     */
    @RequiresPermissions("sys:model:add")
    @RequestMapping(method = RequestMethod.GET,value = "create",name = "创建模型")
    public void create(@RequestParam("name") String name, @RequestParam("key") String key, @RequestParam("description") String description,
                       HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            response.sendRedirect(request.getContextPath() + "/process-editor/modeler.jsp?modelId=" + modelData.getId());
        } catch (Exception e) {
            logger.error("创建模型失败：", e);
        }
    }

    /**
     * 根据Model部署流程
     */
    @RequiresPermissions("sys:model:deploy")
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "/deploy",name = "部署流程")
    public Map deploy(String modelId, RedirectAttributes redirectAttributes) {
        Map map = new HashMap();
        try {
            Model modelData = repositoryService.getModel(modelId);

            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes, "UTF-8")).deploy();
            //redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
            map.put("code","success");
            map.put("deploymentID",deployment.getId());
        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", modelId, e);
        }
        return map;
    }

    /**
     * 导出model对象为指定类型
     *
     * @param modelId 模型ID
     * @param type    导出文件类型(bpmn\json)
     */
    @RequiresPermissions("sys:model:export")
    @RequestMapping(method = RequestMethod.GET,value = "export/{modelId}/{type}",name = "导出文件")
    public void export(@PathVariable("modelId") String modelId,
                       @PathVariable("type") String type,
                       HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export for type: " + type);
                response.flushBuffer();
                return;
            }

            String filename = "";
            byte[] exportBytes = null;

            String mainProcessId = bpmnModel.getMainProcess().getId();

            if (type.equals("bpmn")) {

                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                exportBytes = xmlConverter.convertToXML(bpmnModel);

                filename = mainProcessId + ".bpmn20.xml";
            } else if (type.equals("json")) {

                exportBytes = modelEditorSource;
                filename = mainProcessId + ".json";

            }

            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
        }
    }

    /**
     * 批量删除
     * @param ids
     */
    @RequiresPermissions("sys:model:delete")
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "/batchDelete",name = "删除模型")
    public int delete(@RequestParam(value = "ids[]")  String[]  ids) {
        for (int i=0;i<ids.length;i++){
            repositoryService.deleteModel(ids[i]);
        }
        return 1;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequiresPermissions("sys:model:delete")
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value = "/delete",name = "删除模型")
    public int delete(@RequestParam(value = "id")  String  id) {
        repositoryService.deleteModel(id);
        return 1;
    }
}
