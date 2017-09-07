package com.dcy.dao;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysRole;
import com.dcy.model.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysRole record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SysRole record);

    /**
     *
     * @mbggenerated
     */
    SysRole selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysRole record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysRole record);

    /**
     * 查询所有的数据个数
     * @param sysRole
     * @return
     */
    int getRoleCount(@Param(value = "role") SysRole sysRole);

    /**
     * 分页查询
     * @param bootStrapTable
     * @param sysRole
     * @return
     */
    List<SysRole> getRolePageList(@Param(value = "BST") BootStrapTable bootStrapTable, @Param(value = "role") SysRole sysRole);

    /**
     * 查询角色名字是否存在
     * @param name
     * @return
     */
    int getRoleNameIsRepeat(@Param(value = "name") String name);

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
    int deleteRoleMenu(@Param(value = "roleid") Integer roleid);

    /**
     * 批量删除 role表数据
     * @param ids
     * @return
     */
    int deleteByids(@Param(value = "ids") Integer[] ids);

    /**
     * 根据roleid 查询有哪些菜单
     * @param roleId
     * @return
     */
    List<String> selectRoleMenu(@Param(value = "roleId") Integer roleId);

    /**
     *  查询所有的role
     * @return
     */
    List<SysRole> selectAll();

    /**
     * 根据userid 查询权限
     * @param userId
     * @return
     */
    List<String> getRoleIdByUserId(@Param(value = "userId")Integer userId);
}