package com.dcy.service;

import com.dcy.model.SysMenu;
import com.dcy.model.VuserRoleMenu;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public interface SysMenuService {

    /**
     * 查询全部
     * @return
     */
    List<SysMenu> selectAll();

    /**
     * 根据username 查询菜单
     * @param username
     * @return
     */
    List<VuserRoleMenu> getMenuListByUserName(String username);
}
