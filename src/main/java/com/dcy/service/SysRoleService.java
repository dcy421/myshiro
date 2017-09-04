package com.dcy.service;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysRole;
import com.dcy.model.SysRoleMenu;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public interface SysRoleService {

    /**
     * 添加
     * @mbggenerated
     */
    int insertSelective(SysRole record);

    /**
     *  根据id查询对象
     * @mbggenerated
     */
    SysRole selectByPrimaryKey(Integer id);

    /**
     *  修改role
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysRole record);

    /**
     * 查询所有的数据个数
     * @param sysRole
     * @return
     */
    int getRoleCount(SysRole sysRole);

    /**
     * 分页查询
     * @param bootStrapTable
     * @param sysRole
     * @return
     */
    List<SysRole> getRolePageList(BootStrapTable bootStrapTable,SysRole sysRole);

    /**
     * 查询角色名字是否存在
     * @param name
     * @return
     */
    int getRoleNameIsRepeat(String name);

    /**
     * 批量添加 角色关联菜单表
     * @param sysRoleMenus
     * @return
     */
    int insertRoleMenuBatch(List<SysRoleMenu> sysRoleMenus);

    /**
     * 根据roleid 删除 角色关联菜单表数据
     * @param roleid
     * @return
     */
    int deleteRoleMenu(Integer roleid);

    /**
     * 批量删除 role表数据
     * @param ids
     * @return
     */
    int deleteByids(Integer[] ids);

    /**
     * 根据roleid 查询有哪些菜单
     * @param roleId
     * @return
     */
    List<String> selectRoleMenu(Integer roleId);
}
