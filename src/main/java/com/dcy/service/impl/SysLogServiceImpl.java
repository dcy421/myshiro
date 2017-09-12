package com.dcy.service.impl;

import com.dcy.dao.SysLogMapper;
import com.dcy.model.SysLog;
import com.dcy.service.SysLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/9/12.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogMapper sysLogMapper;

    public int insertSelective(SysLog record) {
        return sysLogMapper.insertSelective(record);
    }
}
