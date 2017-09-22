package com.dcy.service;

import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**流程任务相关Service
 * Created by Administrator on 2017/9/18.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ActTaskService {

    @Resource
    private ProcessEngineFactoryBean processEngineFactoryBean;
    @Resource
    private ProcessEngine processEngine;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private FormService formService;
    @Resource
    private HistoryService historyService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private IdentityService identityService;
}
