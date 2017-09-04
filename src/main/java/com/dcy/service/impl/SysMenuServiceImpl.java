package com.dcy.service.impl;

import com.dcy.dao.SysMenuMapper;
import com.dcy.model.SysMenu;
import com.dcy.model.VuserRoleMenu;
import com.dcy.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    public List<SysMenu> selectAll() {
        return sysMenuMapper.selectAll();
    }

    public List<VuserRoleMenu> getMenuListByUserName(String username) {
        return sysMenuMapper.getMenuListByUserName(username);
    }
}
