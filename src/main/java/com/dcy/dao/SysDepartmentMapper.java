package com.dcy.dao;

import com.dcy.model.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysDepartmentMapper {
    /**
     *  删除
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbggenerated
     */
    int insert(SysDepartment record);

    /**
     *  添加
     * @mbggenerated
     */
    int insertSelective(SysDepartment record);

    /**
     *
     * @mbggenerated
     */
    SysDepartment selectByPrimaryKey(Integer id);

    /**
     *  修改
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysDepartment record);

    /**
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SysDepartment record);

    /**
     * 根据部门id查询所有的部门
     * @param id
     * @return
     */
    List<Map> selectByPrimaryKeyForList(Integer id);

    /**
     * 根据id 查询所在部门和所有的下属
     * @return
     */
    List<Integer> selectByPrimaryKeyForIdList(@Param(value = "id") Integer id);

    /**
     * 根据sql 查询能看到的部门
     * @param sql
     * @return
     */
    List<SysDepartment> selectByPrimaryKeyForIdListRange(@Param(value = "sql") String sql);
}