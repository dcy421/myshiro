package com.dcy.service.impl;

import javax.annotation.Resource;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysUserRole;
import com.dcy.shiro.realm.UserRealm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcy.dao.SysUserMapper;
import com.dcy.model.SysUser;
import com.dcy.service.PasswordHelper;
import com.dcy.service.SysUserService;

import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private PasswordHelper passwordHelper;
	@Resource
	private UserRealm userRealm;


	public int getUserNameIsRepeat(String userName) {
		return sysUserMapper.getUserNameIsRepeat(userName);
	}

	public SysUser selectByUserNamePassWord(String username, String password) {
		return sysUserMapper.selectByUserNamePassWord(username, password);
	}

	public int insertSelective(SysUser sysUser) {
		//赋值加密之后新的密码
		passwordHelper.encryptPassword(sysUser);
		return sysUserMapper.insertSelective(sysUser);
	}

	public SysUser selectByUserName(String username) {
		return sysUserMapper.selectByUserName(username);
	}

	public SysUser selectByPrimaryKey(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(SysUser record) {
		//赋值加密之后新的密码
		//passwordHelper.encryptPassword(record);
		//清空所有的缓存
		userRealm.clearAllCache();
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	public int getUserCount(SysUser sysUser) {
		return sysUserMapper.getUserCount(sysUser);
	}

	public List<SysUser> getUserPageList(BootStrapTable bootStrapTable, SysUser sysUser) {
		return sysUserMapper.getUserPageList(bootStrapTable, sysUser);
	}

	public Set<String> getRoleNameByUserName(String username) {
		return sysUserMapper.getRoleNameByUserName(username);
	}

	public Set<String> getPermissionsByUserName(String username) {
		return sysUserMapper.getPermissionsByUserName(username);
	}

	public int deleteUserRoleByUserId(Integer userId) {
		//清空所有的缓存
		userRealm.clearAllCache();
		return sysUserMapper.deleteUserRoleByUserId(userId);
	}

	public int insertUserRoleBatch(List<SysUserRole> sysUserRoles) {
		//清空所有的缓存
		userRealm.clearAllCache();
		return sysUserMapper.insertUserRoleBatch(sysUserRoles);
	}

	public int deleteByPrimaryKey(Integer id) {
		//清空所有的缓存
		userRealm.clearAllCache();
		return sysUserMapper.deleteByPrimaryKey(id);
	}

	public int deleteByIds(Integer[] ids) {
		//清空所有的缓存
		userRealm.clearAllCache();
		return sysUserMapper.deleteByIds(ids);
	}

}
