package com.dcy.service;


import com.dcy.model.BootStrapTable;
import com.dcy.model.SysUser;
import com.dcy.model.SysUserRole;

import java.util.List;
import java.util.Set;

public interface SysUserService {



    /**
     * 查询用户名是否 重复
     * @param userName
     * @return
     */
    int getUserNameIsRepeat(String userName);

    /**
     * 登陆验证
     * @param username
     * @param password
     * @return
     */
    SysUser selectByUserNamePassWord(String username, String password);

    /**
     * 用户添加
     * @param sysUser
     * @return
     */
    int insertSelective(SysUser sysUser);


    /**
     * 登陆验证  根据username查询对象
     * @param username
     * @return
     */
    SysUser selectByUserName(String username);


    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    SysUser selectByPrimaryKey(Integer id);


    /**
     * 修改 user
     * @param record
     * @return
     */
   int updateByPrimaryKeySelective(SysUser record);


    /**
     * 获取一共有多少条数据
     * @param sysUser
     * @return
     */
    int getUserCount(SysUser sysUser);

    /**
     * 分页
     * @param bootStrapTable
     * @param sysUser
     * @return
     */
    List<SysUser> getUserPageList(BootStrapTable bootStrapTable, SysUser sysUser);

    /**
     * 根据username 查询角色
     * @param username
     * @return
     */
    Set<String> getRoleNameByUserName(String username);

    /**
     * 根据username 查询所有的权限
     * @param username
     * @return
     */
    Set<String> getPermissionsByUserName(String username);


    /**
     * 根据userid 删除当前人的所有角色
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(Integer userId);

    /**
     * 批量添加角色
     * @param sysUserRoles
     * @return
     */
    int insertUserRoleBatch(List<SysUserRole> sysUserRoles);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByIds(Integer[] ids);
   
}
