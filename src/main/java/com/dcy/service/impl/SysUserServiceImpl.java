package com.dcy.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcy.dao.SysUserMapper;
import com.dcy.model.SysUser;
import com.dcy.service.PasswordHelper;
import com.dcy.service.SysUserService;

@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserMapper sysUserMapper;
	@Resource
	private PasswordHelper passwordHelper;
	
	
	public SysUser selectByUserNamePassWord(String username, String password) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectByUserNamePassWord(username, password);
	}

	public int insertSelective(SysUser sysUser) {
		// TODO Auto-generated method stub
		passwordHelper.encryptPassword(sysUser);
		return sysUserMapper.insertSelective(sysUser);
	}

	public SysUser selectByUserName(String username) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectByUserName(username);
	}

	public SysUser selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return sysUserMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(SysUser record) {
		// TODO Auto-generated method stub
		passwordHelper.encryptPassword(record);
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

}
