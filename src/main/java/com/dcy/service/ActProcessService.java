package com.dcy.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**流程定义相关
 * Created by Administrator on 2017/9/15.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActProcessService {

    @Resource
    private RepositoryService repositoryService;


    /**
     * 获取流程定义列表
     * @return
     */
    public List<Deployment> getDeploymentList(String deploymentName){
        List<Deployment> deploymentList = repositoryService.createDeploymentQuery()
                .orderByDeploymenTime().desc()
                .deploymentNameLike("%"+deploymentName+"%")
                .list();
        return deploymentList;

    }

    /**
     * 获取流程定义列表
     * @return
     */
    public List<ProcessDefinition> getProcessList(){
        List<ProcessDefinition> list = repositoryService//与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()//创建一个流程定义的查询
                /**指定查询条件,where条件*/
//						.deploymentId(deploymentId)//使用部署对象ID查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
//						.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /**排序*/
                .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
//						.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                /**返回的结果集*/
                .list();//返回一个集合列表，封装流程定义
//						.singleResult();//返回惟一结果集
//						.count();//返回结果集数量
//						.listPage(firstResult, maxResults);//分页查询
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:"+pd.getResourceName());
                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
                System.out.println("部署对象ID："+pd.getDeploymentId());
                System.out.println("#########################################################");
            }
        }
        return list;
    }

    /**
     * 附加功能：查询最新版本的流程定义
     * @return
     */
    public List<ProcessDefinition> findLastVersionProcessDefinition(){
        List<ProcessDefinition> list = repositoryService//
                .createProcessDefinitionQuery()//
                .orderByProcessDefinitionVersion().asc()//使用流程定义的版本升序排列
                .list();
        /**
         * Map<String,ProcessDefinition>
         map集合的key：流程定义的key
         map集合的value：流程定义的对象
         map集合的特点：当map集合key值相同的情况下，后一次的值将替换前一次的值
         */
        Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
        if(list!=null && list.size()>0){
            for(ProcessDefinition pd:list){
                map.put(pd.getKey(), pd);
            }
        }
        List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
        if(pdList!=null && pdList.size()>0){
            for(ProcessDefinition pd:pdList){
                System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:"+pd.getResourceName());
                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
                System.out.println("部署对象ID："+pd.getDeploymentId());
                System.out.println("#########################################################");
            }
        }
        return list;
    }

    /**
     * 删除流程定义
     * @param deploymentId
     */
    public void deleteDeployment(String deploymentId){
        repositoryService.deleteDeployment(deploymentId, true);
    }


    /**
     * 获取流程图片
     * @param deploymentId
     * @throws IOException
     */
    public void viewPic(String deploymentId) throws IOException {
        //获取图片资源名称
        List<String> list = repositoryService//
                .getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if(list!=null && list.size()>0){
            for(String name:list){
                if(name.indexOf(".png")>=0){
                    resourceName = name;
                }
            }
        }

        //获取图片的输入流
        InputStream in = repositoryService//
                .getResourceAsStream(deploymentId, resourceName);

        //将图片生成到D盘的目录下
        File file = new File("D:/"+resourceName);
        //将输入流的图片写到D盘下
        FileUtils.copyInputStreamToFile(in, file);
    }
}
