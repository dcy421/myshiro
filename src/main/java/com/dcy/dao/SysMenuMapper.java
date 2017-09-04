package com.dcy.dao;

import com.dcy.model.SysMenu;

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
}