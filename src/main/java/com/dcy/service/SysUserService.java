package com.dcy.service;


import com.dcy.model.BootStrapTable;
import com.dcy.model.SysUser;

import java.util.List;

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
   
}
