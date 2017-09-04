package com.dcy.controller;

import com.dcy.model.MenuTreeNode;
import com.dcy.model.SysMenu;
import com.dcy.model.VuserRoleMenu;
import com.dcy.service.SysMenuService;
import com.dcy.service.SysRoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/31.
 */
@Controller
@RequestMapping("${adminPath}/menu")
public class MenuController {
    private Logger logger = Logger.getLogger(MenuController.class);

    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysRoleService sysRoleService;

    /**
     * 角色分页查询
     * @return
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/getMenuList")
    public List<Map> getMenuList(String flag, Integer roleId){
        List<Map> sysMenuList = new ArrayList<Map>();
        List<SysMenu> sysMenus = sysMenuService.selectAll();
        try{
            //添加时候使用 查询所有的
            if ("0".equals(flag)){
                if (sysMenus.size()>0){
                    for (SysMenu sysmenu:sysMenus) {
                        Map map = new HashMap();
                        map.put("id",sysmenu.getId());
                        map.put("pId",sysmenu.getParentid());
                        map.put("name",sysmenu.getName());
                        sysMenuList.add(map);
                    }
                }
            }else{//修改
                List<String> roleMenuIds =sysRoleService.selectRoleMenu(roleId);//查询本人的权限
                for (SysMenu sysmenu:sysMenus) {
                    Map map = new HashMap();
                    map.put("id",sysmenu.getId());
                    map.put("pId",sysmenu.getParentid());
                    map.put("name",sysmenu.getName());
                    //包含这个字符串
                    if (isBelongList(roleMenuIds,String.valueOf(sysmenu.getId())))
                        map.put("checked",true);
                    sysMenuList.add(map);
                }
            }
        }catch (Exception e){
            logger.error("getMenuList-=-:"+e.toString());
        }
        return sysMenuList;
    }

    /**
     * 左侧菜单树
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.GET,value="/getMenuListByUserName")
    public List<MenuTreeNode> getMenuListByUserName(String userName){
        //定义空的list
        List<MenuTreeNode> sysMenuList = new ArrayList<MenuTreeNode>();
        //根据用户查询所有的权限
        List<VuserRoleMenu> vuserRoleMenuList = sysMenuService.getMenuListByUserName(userName);
        if (vuserRoleMenuList.size() > 0){
            //取最大的父级
            VuserRoleMenu vuserRoleMenu = vuserRoleMenuList.get(0);
            //取第一条数据
            MenuTreeNode menuTreeNode = new MenuTreeNode(vuserRoleMenu.getMenuid(),vuserRoleMenu.getParentId(),false,vuserRoleMenu.getMenuname(),false,vuserRoleMenu.getIcon(),vuserRoleMenu.getHref(),vuserRoleMenu.getTarget());
            for (int i = 1; i < vuserRoleMenuList.size(); i++) {
                //循环添加子集数据
                MenuTreeNode menuTreeNode2 = new MenuTreeNode(vuserRoleMenuList.get(i).getMenuid(),vuserRoleMenuList.get(i).getParentId(),false,vuserRoleMenuList.get(i).getMenuname(),false,vuserRoleMenuList.get(i).getIcon(),vuserRoleMenuList.get(i).getHref(),vuserRoleMenuList.get(i).getTarget());
                menuTreeNode.add(menuTreeNode2);
            }
            return menuTreeNode.getChildren();
        }else {
            return sysMenuList;
        }
    }

    /**
     * 判断对象是否有这个值
     * @param roleMenuIds
     * @param str
     * @return
     */
    private boolean isBelongList(List<String> roleMenuIds,String str){
        boolean bResult = false;
        for (String temp : roleMenuIds) {
            if (temp.equals(str)) {
                bResult = true;
                break;
            }
        }
        return bResult;
    }
}
