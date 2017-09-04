package com.dcy.dao;

import com.dcy.model.SysDepartment;

public interface SysDepartmentMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysDepartment record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SysDepartment record);

    /**
     *
     * @mbggenerated
     */
    SysDepartment selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysDepartment record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysDepartment record);
}