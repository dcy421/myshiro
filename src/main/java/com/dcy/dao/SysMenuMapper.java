package com.dcy.dao;

import com.dcy.model.SysMenu;
import com.dcy.model.VuserRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysMenu record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SysMenu record);

    /**
     *
     * @mbggenerated
     */
    SysMenu selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysMenu record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysMenu record);

    /**
     * 查询全部
     * @return
     */
    List<SysMenu> selectAll();

    /**
     * 根据username 查询菜单
     * @param username
     * @return
     */
    List<VuserRoleMenu> getMenuListByUserName(@Param(value = "username") String username);
}