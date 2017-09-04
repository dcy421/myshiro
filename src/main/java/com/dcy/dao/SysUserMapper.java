package com.dcy.dao;

import com.dcy.model.SysUser;
import org.apache.ibatis.annotations.Param;

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
}