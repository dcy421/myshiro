package com.dcy.service;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysLog;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */
public interface SysLogService {

    /**
     *  添加
     * @mbggenerated
     */
    int insertSelective(SysLog record);

    /**
     * 获取一共有多少条数据
     * @param sysLog
     * @return
     */
    int getLogCount(SysLog sysLog);

    /**
     * 分页
     * @param bootStrapTable
     * @param sysLog
     * @return
     */
    List<SysLog> getLogPageList(BootStrapTable bootStrapTable, SysLog sysLog);
}
