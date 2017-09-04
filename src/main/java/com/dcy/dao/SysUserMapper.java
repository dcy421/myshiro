package com.dcy.dao;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysUser record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SysUser record);

    /**
     *
     * @mbggenerated
     */
    SysUser selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysUser record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysUser record);

    /**
     * 查询用户名是否 重复
     * @param userName
     * @return
     */
    int getUserNameIsRepeat(@Param(value = "userName") String userName);
    /**
     * 登陆验证
     * @param username
     * @param password
     * @return
     */
    SysUser selectByUserNamePassWord(@Param(value = "username") String username, @Param(value = "password") String password);


    /**
     * 登陆验证  根据username查询对象
     * @param username
     * @return
     */
    SysUser selectByUserName(@Param(value = "username") String username);


    /**
     * 获取一共有多少条数据
     * @param sysUser
     * @return
     */
    int getUserCount(@Param(value = "user") SysUser sysUser);

    /**
     * 分页
     * @param bootStrapTable
     * @param sysUser
     * @return
     */
    List<SysUser> getUserPageList(@Param(value = "BST") BootStrapTable bootStrapTable,@Param(value = "user") SysUser sysUser);
}