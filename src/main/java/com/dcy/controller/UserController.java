package com.dcy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dcy.config.Global;
import com.dcy.model.BootStrapTable;
import com.dcy.model.SysRole;
import com.dcy.model.SysUserRole;
import com.dcy.service.SysDepartmentService;
import com.dcy.service.SysDictService;
import com.dcy.service.SysRoleService;
import com.dcy.utils.Common;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dcy.model.SysUser;
import com.dcy.service.SysUserService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * 用户控制器
 */
@Controller
@RequestMapping("${adminPath}/sys/user")
public class UserController extends BaseController {
	
	private Logger logger = Logger.getLogger(UserController.class);

	/**
	 * 用户service
	 */
	@Resource
	private SysUserService sysUserService;
	/**
	 * 字典service
	 */
	@Resource
	private SysDictService sysDictService;
	/**
	 * 角色service
	 */
	@Resource
	private SysRoleService sysRoleService;
	/**
	 * 部门service
	 */
	@Resource
	private SysDepartmentService sysDepartmentService;


	/**
	 * 跳转 人员首页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:search")
	@RequestMapping(method= RequestMethod.GET,value = {"/index"},name = "人员首页")
	public String index(HttpServletRequest request, Model model) {
		return "/sys/userIndex";
	}

	/**
	 * 分页数据
	 * @param bootStrapTable
	 * @param sysUser
	 * @return
	 */
	@RequiresPermissions("sys:user:search")
	@ResponseBody
	@RequestMapping(method= RequestMethod.POST,value={"/getUserPageList"},name = "人员分页数据")
	public Map getUserList(BootStrapTable bootStrapTable, SysUser sysUser){
		Map map = new HashMap();
		try {
			map = new Common().getBSTData(sysUserService.getUserCount(sysUser), sysUserService.getUserPageList(bootStrapTable,sysUser));
		}catch (Exception ex){
			logger.error("getUserList-=-"+ex.toString());
		}
		return map;
	}

	/**
	 * 添加用户页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(method=RequestMethod.GET,value="/add",name = "添加用户页面")
	public String add(HttpServletRequest request,HttpServletResponse response,Model model) {
		//logger.info("用户添加 Get");
		try {
			request.setAttribute("sexList",sysDictService.selectListByType("sex"));
			request.setAttribute("dataRangeList",sysDictService.selectListByType("data_range"));
			request.setAttribute("roleList",sysRoleService.selectAll());
			request.setAttribute("depList",sysDepartmentService.selectByPrimaryKeyForIdListRange());
		}catch (Exception ex){
			logger.error("add-=-"+ex.toString());
		}
		return "/sys/userAdd";
	}

	/**
	 * 修改用户页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(method=RequestMethod.GET,value="/update",name = "修改用户页面")
	public String update(HttpServletRequest request,Integer id) {
		//logger.info("用户添加 Get");
		List<SysRole> roleoldList = new ArrayList<SysRole>();
		List<SysRole> rolenewList = new ArrayList<SysRole>();
		try {
			request.setAttribute("sexList",sysDictService.selectListByType("sex"));
			request.setAttribute("dataRangeList",sysDictService.selectListByType("data_range"));
			request.setAttribute("user",sysUserService.selectByPrimaryKey(id));
			request.setAttribute("depList",sysDepartmentService.selectByPrimaryKeyForIdListRange());
			List<SysRole> sysRoleList = sysRoleService.selectAll();
			//返回当前人的权限
			List<String> strings = sysRoleService.getRoleIdByUserId(id);
			for (SysRole s:sysRoleList){
				//int 转换字符串
				if (isBelongList(strings,String.valueOf(s.getId()))){
					//如果存在 就表示当前人有这个角色 往newlist添加
					rolenewList.add(s);
				}else {
					//否则往 oldlist添加
					roleoldList.add(s);
				}
			}
			//人员没有这个角色
			request.setAttribute("roleoldList",roleoldList);
			//人员有这个角色
			request.setAttribute("rolenewList",rolenewList);
		}catch (Exception ex){
			logger.error("update-=-"+ex.toString());
		}
		return "/sys/userUpd";
	}

	/**
	 *  保存user
	 * @param sysUser  对象
	 * @param model
	 * @param flag  add添加   update 修改
	 * @param roleids   角色ids
	 * @return
	 */
	@RequiresPermissions(value={"sys:user:add","sys:user:update"},logical= Logical.OR)
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST,value="/save",name = "保存user")
	public int save(SysUser sysUser,Model model,String flag,@RequestParam(value = "roleids")  Integer[]  roleids) {
		//logger.info("用户添加 Post");
		int count = 0;
		try {
			if ("add".equalsIgnoreCase(flag)){
				count = sysUserService.insertSelective(sysUser);
				if (count > 0 && roleids.length > 0){
					//根据userid 和roleids  返回一个list userrole对象  如果有对象 那就执行批量添加
					getUserRoles(sysUser.getId(),roleids);
				}
			}else {
				count = sysUserService.updateByPrimaryKeySelective(sysUser);
				if (count > 0 && roleids.length > 0){
					//删除user_role表数据
					sysUserService.deleteUserRoleByUserId(sysUser.getId());
					//根据userid 和roleids  返回一个list userrole对象  如果有对象 那就执行批量添加
					getUserRoles(sysUser.getId(),roleids);
				}
			}
		} catch (Exception e) {
			logger.error("save-=-"+e.toString());
		}
		return count;
	}

	/**
	 * 根据userid 和roleids  返回一个list userrole对象  如果有对象 那就执行批量添加
	 * @param userID
	 * @param roleids
	 * @return
	 */
	private void getUserRoles(Integer userID,Integer[] roleids){
		List<SysUserRole> sysUserRoleList = new ArrayList<SysUserRole>();
		for (int i = 0; i < roleids.length; i++) {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(userID);
			sysUserRole.setRoleId(roleids[i]);
			sysUserRoleList.add(sysUserRole);
		}
		if (sysUserRoleList.size()>0){
			sysUserService.insertUserRoleBatch(sysUserRoleList);//批量添加
		}
	}
	/**
	 * 检测是否有这个账户
	 * @param userName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method= RequestMethod.POST,value="/getRepeatUserName",name = "检测是否有这个账户")
	public int getRepeatUserName(String userName){
		int count = 0;
		try {
			count = sysUserService.getUserNameIsRepeat(userName);
		}catch (Exception e){
			logger.error("getRepeatUserName-=-:"+e.toString());
		}
		return count;
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@ResponseBody
	@RequestMapping(method= RequestMethod.POST,value="/delete",name = "批量删除用户")
	public int delete(@RequestParam(value = "ids[]")  Integer[]  ids){
		int count = 0;
		try {
			count =sysUserService.deleteByIds(ids);
		}catch (Exception e){
			logger.error("delete-=-:"+e.toString());
		}
		return count;
	}

	/**
	 * 上传头像
	 * @param files
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadPicture",name = "上传头像")
	public String uploads(@RequestParam("file")MultipartFile files, HttpServletRequest request, HttpServletResponse response) {
		try {
			//一个文件
			super.upload(files, Global.USER,request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.getFileName();
	}


	private boolean isBelongList(List<String> roleMenuIds,String str){
		boolean bResult = false;
		for (String temp : roleMenuIds) {
			if (temp.equalsIgnoreCase(str)) {
				bResult = true;
				break;
			}
		}
		return bResult;
	}
}
