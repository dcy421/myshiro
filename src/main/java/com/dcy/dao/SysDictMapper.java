package com.dcy.dao;

import com.dcy.model.SysDict;

public interface SysDictMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysDict record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SysDict record);

    /**
     *
     * @mbggenerated
     */
    SysDict selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysDict record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysDict record);
}