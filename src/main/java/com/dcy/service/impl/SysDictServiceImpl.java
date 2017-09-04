package com.dcy.service.impl;

import com.dcy.dao.SysDictMapper;
import com.dcy.model.SysDict;
import com.dcy.service.SysDictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDictServiceImpl implements SysDictService {

    @Resource
    private SysDictMapper sysDictMapper;

    public List<SysDict> selectListByType(String type) {
        return sysDictMapper.selectListByType(type);
    }
}
