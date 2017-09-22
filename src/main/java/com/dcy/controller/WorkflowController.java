package com.dcy.controller;

import com.dcy.model.BootStrapTable;
import com.dcy.service.ActProcessService;
import com.dcy.service.ActTaskService;
import com.dcy.utils.DateJsonValueProcessor;
import com.dcy.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**工作流控制器
 * Created by Administrator on 2017/9/18.
 */
@Controller
@RequestMapping("${adminPath}/sys/workf")
public class WorkflowController extends BaseController {

    private Logger logger = Logger.getLogger(WorkflowController.class);
    /**
     * 部署的service
     */
    @Resource
    private ActProcessService actProcessService;
    @Resource
    private RepositoryService repositoryService;
    /**
     * 任务的service
     */
    @Resource
    private ActTaskService actTaskService;

    @RequestMapping(method= RequestMethod.GET,value = {"/index"},name = "工作流部署首页")
    public String index(HttpServletRequest request, Model model) {
        return "/act/workflowIndex";
    }


    @RequestMapping(method= RequestMethod.GET,value = {"/add"},name = "工作流部署添加")
    public String add(HttpServletRequest request, Model model) {
        return "/act/deploymentAdd";
    }

    /**
     *  分页数据
     * @return
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value={"/getDeploymentList"},name = "工作流部署分页数据")
    public String getDeploymentList(BootStrapTable bootStrapTable,HttpServletResponse response, String deploymentName){
        try{
            List<Deployment> deploymentList =  repositoryService.createDeploymentQuery()
                    .orderByDeploymenTime().desc()
                    .deploymentNameLike("%"+deploymentName+"%")
                    .listPage(bootStrapTable.getOffset(),bootStrapTable.getLimit());
            long total=repositoryService.createDeploymentQuery().deploymentNameLike("%"+deploymentName+"%").count(); // 获取总记录数
            //为了过滤有些字段不需要形成json
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.setExcludes(new String[]{"resources"});
            //格式化日期
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            JSONArray jsonArray= JSONArray.fromObject(deploymentList,jsonConfig);
            JSONObject result=new JSONObject();
            result.put("rows", jsonArray);
            result.put("total", total);
            ResponseUtil.write(response, result);
        }catch (Exception ex){
            logger.error("getProcessList-=-"+ex.toString());
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value = {"/delete"},name = "工作流部署删除")
    public int deleteDeployment(HttpServletRequest request, Model model,@RequestParam(value = "ids[]")  String[] deploymentIds) {
        for (int i = 0;i<deploymentIds.length;i++){
            actProcessService.deleteDeployment(deploymentIds[i]);
        }
        return 1;
    }


    /**
     * 上传流程部署文件
     * @param deployFile
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/deploy")
    public int deploy(@RequestParam("file") MultipartFile deployFile, HttpServletResponse response)throws Exception{
        repositoryService.createDeployment() // 创建部署
                .name(deployFile.getOriginalFilename()) // 流程名称
                .addZipInputStream(new ZipInputStream(deployFile.getInputStream())) // 添加zip输入流
                .deploy(); // 部署
        return 1;
    }


    @RequestMapping(method= RequestMethod.GET,value = {"/proindex"},name = "工作流流程首页")
    public String processDefinitionIndex(HttpServletRequest request, Model model) {
        return "/act/processDefinitionIndex";
    }


    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value={"/getProcessDeList"},name = "工作流部署分页数据")
    public String getProcessDeList(BootStrapTable bootStrapTable,HttpServletResponse response,String processName){
        try{

            List<ProcessDefinition> processDefinition=repositoryService.createProcessDefinitionQuery() // 创建流程流程定义查询
                    .orderByProcessDefinitionId().desc() // 根据流程定义id降序排列
                    .processDefinitionNameLike("%"+processName+"%") // 根据流程定义名称模糊查询
                    .listPage(bootStrapTable.getOffset(),bootStrapTable.getLimit());
            long total=repositoryService.createProcessDefinitionQuery().processDefinitionNameLike("%"+processName+"%").count(); // 获取总记录数
            //为了过滤有些字段不需要形成json
            JsonConfig jsonConfig=new JsonConfig();
            jsonConfig.setExcludes(new String[]{"identityLinks","processDefinition"});
            jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd hh:mm:ss"));
            JSONArray jsonArray= JSONArray.fromObject(processDefinition,jsonConfig);
            JSONObject result=new JSONObject();
            result.put("rows", jsonArray);
            result.put("total", total);
            ResponseUtil.write(response, result);
        }catch (Exception ex){
            logger.error("getProcessList-=-"+ex.toString());
        }
        return null;
    }

    @RequestMapping(method= RequestMethod.GET,value={"/showView"},name = "查询流程图片")
    public String showView(String deploymentId,String diagramResourceName,HttpServletResponse response)throws Exception{
        InputStream inputStream=repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
        OutputStream out=response.getOutputStream();
        for(int b=-1;(b=inputStream.read())!=-1;){
            out.write(b);
        }
        out.close();
        inputStream.close();
        return null;
    }

}
