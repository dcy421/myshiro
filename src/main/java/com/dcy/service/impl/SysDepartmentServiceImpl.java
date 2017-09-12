package com.dcy.service.impl;

import com.dcy.dao.SysDepartmentMapper;
import com.dcy.model.SysDepartment;
import com.dcy.service.BaseService;
import com.dcy.service.SysDepartmentService;
import com.dcy.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysDepartmentServiceImpl extends BaseService implements SysDepartmentService {

    @Resource
    private SysDepartmentMapper sysDepartmentMapper;


    public List<Map> selectByPrimaryKeyForList(Integer id) {
        return sysDepartmentMapper.selectByPrimaryKeyForList(id);
    }

    public int insertSelective(SysDepartment record) {
        return sysDepartmentMapper.insertSelective(record);
    }

    public int deleteByPrimaryKey(Integer id) {
        return sysDepartmentMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(SysDepartment record) {
        return sysDepartmentMapper.updateByPrimaryKeySelective(record);
    }

    public List<SysDepartment> selectByPrimaryKeyForIdListRange() {
        String sql = dataScopeDepFilter(UserUtils.getUser(), "");
        return sysDepartmentMapper.selectByPrimaryKeyForIdListRange(sql);
    }
}
