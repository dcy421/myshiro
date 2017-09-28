package com.dcy.controller;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysLog;
import com.dcy.service.SysLogService;
import com.dcy.utils.Common;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/12.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

    private Logger logger = Logger.getLogger(UserController.class);

    /**
     * 日志service
     */
    @Resource
    private SysLogService sysLogService;

    /**
     * 日志首页
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("sys:log:search")
    @RequestMapping(method= RequestMethod.GET,value = {"/index"},name = "日志首页")
    public String index(HttpServletRequest request, Model model) {
        return "/sys/logIndex";
    }


    /**
     * Log 分页数据
     * @param bootStrapTable
     * @param sysLog
     * @return
     */
    @RequiresPermissions("sys:log:search")
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value={"/getLogPageList"},name = "日志分页数据")
    public Map getLogList(BootStrapTable bootStrapTable, SysLog sysLog){
        Map map = new HashMap();
        try {
            Integer count = sysLogService.getLogCount(sysLog);
            if (count > 0){
                map = new Common().getBSTData(count,sysLogService.getLogPageList(bootStrapTable, sysLog));
            }else {
                map = new Common().getBSTData(count,new ArrayList());
            }

        }catch (Exception ex){
            logger.error("getUserList-=-"+ex.toString());
        }
        return map;
    }
}
