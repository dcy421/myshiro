package com.dcy.service.impl;

import com.dcy.dao.SysRoleMapper;
import com.dcy.model.BootStrapTable;
import com.dcy.model.SysRole;
import com.dcy.model.SysRoleMenu;
import com.dcy.service.BaseService;
import com.dcy.service.SysRoleService;
import com.dcy.shiro.realm.UserRealm;
import com.dcy.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends BaseService implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private UserRealm userRealm;

    public int insertSelective(SysRole record) {
        return sysRoleMapper.insertSelective(record);
    }

    public SysRole selectByPrimaryKey(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysRole record) {
        //清空所有的缓存
        userRealm.clearAllCache();
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    public int getRoleCount(SysRole sysRole) {
        //sql 过滤
        sysRole.setSql(dataScopeFilter(UserUtils.getUser(), "r"));
        return sysRoleMapper.getRoleCount(sysRole);
    }

    public List<SysRole> getRolePageList(BootStrapTable bootStrapTable, SysRole sysRole) {
        //sql 过滤
        sysRole.setSql(dataScopeFilter(UserUtils.getUser(), "r"));
        return sysRoleMapper.getRolePageList(bootStrapTable, sysRole);
    }

    public int getRoleNameIsRepeat(String name) {
        return sysRoleMapper.getRoleNameIsRepeat(name);
    }

    public int insertRoleMenuBatch(List<SysRoleMenu> sysRoleMenus) {
        return sysRoleMapper.insertRoleMenuBatch(sysRoleMenus);
    }

    public int deleteRoleMenu(Integer roleid) {
        //清空所有的缓存
        userRealm.clearAllCache();
        return sysRoleMapper.deleteRoleMenu(roleid);
    }

    public int deleteByids(Integer[] ids) {
        //清空所有的缓存
        userRealm.clearAllCache();
        return sysRoleMapper.deleteByids(ids);
    }

    public List<String> selectRoleMenu(Integer roleId) {
        return sysRoleMapper.selectRoleMenu(roleId);
    }

    public List<SysRole> selectAll() {
        return sysRoleMapper.selectAll();
    }

    public List<String> getRoleIdByUserId(Integer userId) {
        return sysRoleMapper.getRoleIdByUserId(userId);
    }
}
