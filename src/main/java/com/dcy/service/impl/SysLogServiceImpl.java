package com.dcy.service.impl;

import com.dcy.dao.SysLogMapper;
import com.dcy.model.BootStrapTable;
import com.dcy.model.SysLog;
import com.dcy.service.BaseService;
import com.dcy.service.SysLogService;
import com.dcy.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/9/12.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseService implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    public int insertSelective(SysLog record) {
        return sysLogMapper.insertSelective(record);
    }

    public int getLogCount(SysLog sysLog) {
        //sql 过滤
        sysLog.setSql(dataScopeFilter(UserUtils.getUser(), "l"));
        return sysLogMapper.getLogCount(sysLog);
    }

    public List<SysLog> getLogPageList(BootStrapTable bootStrapTable, SysLog sysLog) {
        //sql 过滤
        sysLog.setSql(dataScopeFilter(UserUtils.getUser(), "l"));
        return sysLogMapper.getLogPageList(bootStrapTable, sysLog);
    }
}
