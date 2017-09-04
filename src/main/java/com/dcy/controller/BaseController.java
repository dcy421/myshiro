package com.dcy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 控制器支持类
 * Created by Administrator on 2017/8/31.
 */
public abstract class BaseController {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(BaseController.class);
    /**
     * 管理基础路径
     */
    @Value("${adminPath}")
    protected String adminPath;
}
