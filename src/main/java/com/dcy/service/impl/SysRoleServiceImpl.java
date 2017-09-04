package com.dcy.service.impl;

import com.dcy.dao.SysRoleMapper;
import com.dcy.model.BootStrapTable;
import com.dcy.model.SysRole;
import com.dcy.model.SysRoleMenu;
import com.dcy.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    public int insertSelective(SysRole record) {
        return sysRoleMapper.insertSelective(record);
    }

    public SysRole selectByPrimaryKey(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysRole record) {
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    public int getRoleCount(SysRole sysRole) {
        return sysRoleMapper.getRoleCount(sysRole);
    }

    public List<SysRole> getRolePageList(BootStrapTable bootStrapTable, SysRole sysRole) {
        return sysRoleMapper.getRolePageList(bootStrapTable, sysRole);
    }

    public int getRoleNameIsRepeat(String name) {
        return sysRoleMapper.getRoleNameIsRepeat(name);
    }

    public int insertRoleMenuBatch(List<SysRoleMenu> sysRoleMenus) {
        return sysRoleMapper.insertRoleMenuBatch(sysRoleMenus);
    }

    public int deleteRoleMenu(Integer roleid) {
        return sysRoleMapper.deleteRoleMenu(roleid);
    }

    public int deleteByids(Integer[] ids) {
        return sysRoleMapper.deleteByids(ids);
    }

    public List<String> selectRoleMenu(Integer roleId) {
        return sysRoleMapper.selectRoleMenu(roleId);
    }
}
