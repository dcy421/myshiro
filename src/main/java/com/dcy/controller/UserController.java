package com.dcy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcy.model.SysUser;
import com.dcy.service.SysUserService;

@Controller
@RequestMapping("${adminPath}/user")
public class UserController extends BaseController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private SysUserService sysUserService;
	
	@RequestMapping(method=RequestMethod.GET,value="/add")
	public String userAdd(HttpServletRequest request,HttpServletResponse response,Model model) {
		logger.info("用户添加 Get");
		return "/sys/userAdd";
	}

	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/add")
	public int userAdd(SysUser sysUser,Model model) {
		logger.info("用户添加 Post");
		int count = 0;
		try {
			count = sysUserService.insertSelective(sysUser);
		} catch (Exception e) {
			logger.error("-=-userAdd-=-"+e.toString());
		}
		return count;
	}
}
