package com.dcy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dcy.model.BootStrapTable;
import com.dcy.service.SysDictService;
import com.dcy.utils.Common;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcy.model.SysUser;
import com.dcy.service.SysUserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("${adminPath}/user")
public class UserController extends BaseController {
	
	private Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysDictService sysDictService;


	/**
	 * 跳转 人员首页
	 * @param sysUser
	 * @param model
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET,value = {"/index"})
	public String index(SysUser sysUser, Model model) {
		return "/sys/userIndex";
	}

	/**
	 * 分页数据
	 * @param bootStrapTable
	 * @param sysUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method= RequestMethod.POST,value={"/getUserPageList"})
	public Map getUserList(BootStrapTable bootStrapTable, SysUser sysUser){
		Map map = new HashMap();
		try {
			map = new Common().getBSTData(sysUserService.getUserCount(sysUser), sysUserService.getUserPageList(bootStrapTable,sysUser));
		}catch (Exception ex){
			logger.error("getUserList-=-"+ex.toString());
		}
		return map;
	}

	@RequestMapping(method=RequestMethod.GET,value="/add")
	public String userAdd(HttpServletRequest request,HttpServletResponse response,Model model) {
		//logger.info("用户添加 Get");
		request.setAttribute("sexList",sysDictService.selectListByType("sex"));
		return "/sys/userAdd";
	}

	/**
	 * 保存user
	 * @param sysUser
	 * @param model
	 * @param flag add 添加  update  修改
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/SaveSysUser")
	public int userAdd(SysUser sysUser,Model model,String flag) {
		//logger.info("用户添加 Post");
		int count = 0;
		try {
			if ("add".equalsIgnoreCase(flag)){
				count = sysUserService.insertSelective(sysUser);
			}else {
				count = sysUserService.updateByPrimaryKeySelective(sysUser);
			}
		} catch (Exception e) {
			logger.error("userAdd-=-"+e.toString());
		}
		return count;
	}

	/**
	 * 检测是否有这个账户
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method= RequestMethod.POST,value="/getRepeatUserName")
	public int getRepeatUserName(String userName){
		int count = 0;
		try {
			count = sysUserService.getUserNameIsRepeat(userName);
		}catch (Exception e){
			logger.error("getRepeatUserName-=-:"+e.toString());
		}
		return count;
	}
}
