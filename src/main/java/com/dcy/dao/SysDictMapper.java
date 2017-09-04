package com.dcy.dao;

import com.dcy.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据类型查询字典
     * @param type
     * @return
     */
    List<SysDict> selectListByType(@Param(value = "type") String type);
}