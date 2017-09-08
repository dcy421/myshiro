package com.dcy.service;

import com.dcy.model.SysDepartment;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/8.
 */
public interface SysDepartmentService {

    /**
     * 根据部门id查询所有的部门
     * @param id
     * @return
     */
    List<Map> selectByPrimaryKeyForList(Integer id);

    /**
     * 添加
     * @mbggenerated
     */
    int insertSelective(SysDepartment record);

    /**
     *  删除
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);


    /**
     *  修改
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SysDepartment record);
}
