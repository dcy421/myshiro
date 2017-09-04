package com.dcy.service;


import com.dcy.model.SysUser;

public interface SysUserService {


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
   
}
