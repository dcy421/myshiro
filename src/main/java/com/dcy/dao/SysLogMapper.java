package com.dcy.dao;

import com.dcy.model.SysLog;

public interface SysLogMapper {
    /**
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysLog record);

    /**
     *
     * @mbggenerated
     */
    int insertSelective(SysLog record);

    /**
     *
     * @mbggenerated
     */
    SysLog selectByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysLog record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysLog record);
}