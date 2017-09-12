package com.dcy.dao;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    int insertSelective(SysLog record);

    /**
     *
     * @mbggenerated
     */
    SysLog selectByPrimaryKey(Integer id);

    /**
     * 获取一共有多少条数据
     * @param sysLog
     * @return
     */
    int getLogCount(@Param(value = "log") SysLog sysLog);

    /**
     * 分页
     * @param bootStrapTable
     * @param sysLog
     * @return
     */
    List<SysLog> getLogPageList(@Param(value = "BST") BootStrapTable bootStrapTable, @Param(value = "log") SysLog sysLog);
}