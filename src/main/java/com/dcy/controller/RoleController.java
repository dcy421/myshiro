package com.dcy.controller;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysRole;
import com.dcy.model.SysRoleMenu;
import com.dcy.service.SysDepartmentService;
import com.dcy.service.SysDictService;
import com.dcy.service.SysRoleService;
import com.dcy.utils.Common;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/31.
 */
@Controller
@RequestMapping("${adminPath}/sys/role")
public class RoleController {
    private Logger logger = Logger.getLogger(RoleController.class);
    /**
     * 角色service
     */
    @Resource
    private SysRoleService sysRoleService;
    /**
     * 字典service
     */
    @Resource
    private SysDictService sysDictService;
    /**
     * 部门service
     */
    @Resource
    private SysDepartmentService sysDepartmentService;

    /**
     * 跳转页面
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:search")
    @RequestMapping(method =RequestMethod.GET, value = "/index",name = "角色首页")
    public String index(HttpServletRequest request, Model model){
        return "/sys/roleIndex";
    }


    /**
     * 角色分页查询
     * @param bootStrapTable
     * @param sysRole
     * @return
     */
    @RequiresPermissions("sys:role:search")
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/getRolePageList",name = "角色分页查询")
    public Map getRolePageList(BootStrapTable bootStrapTable, SysRole sysRole){
        Map map = new HashMap();
        try{
            Integer count = sysRoleService.getRoleCount(sysRole);
            if (count > 0){
                map = new Common().getBSTData(count, sysRoleService.getRolePageList(bootStrapTable,sysRole));
            }else {
                map = new Common().getBSTData(count, new ArrayList());
            }

        }catch (Exception ex){
            logger.error("getRolePageList-=-"+ex.toString());
        }
        return map;
    }

    /**
     * 跳转添加页面
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:add")
    @RequestMapping(method= RequestMethod.GET, value = "/add",name = "添加角色页面")
    public String roleAdd(HttpServletRequest request, Model model){
        request.setAttribute("depList",sysDepartmentService.selectByPrimaryKeyForIdListRange());
        return "/sys/roleAdd";
    }

    /**
     * 检测是否有这个角色
     * @param roleName
     * @return
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/getRepeatRoleName",name = "检测是否有这个角色")
    public int getRepeatRoleName(String roleName){
        int count = 0;
        try {
            if (sysRoleService.getRoleNameIsRepeat(roleName)>0)
                count = 1;
        }catch (Exception e){
            logger.error("getRepeatRoleName-=-:"+e.toString());
        }
        return count;
    }


    /**
     * 角色添加修改操作
     * @param sysRole
     * @param flag  添加或者修改
     * @param ids   权限级的数组
     * @return
     */
    @RequiresPermissions(value={"sys:role:add","sys:role:update"},logical= Logical.OR)
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/save",name = "角色添加修改操作")
    public int SaveSysRole(SysRole sysRole,String flag,@RequestParam(value = "ids")  Integer[]  ids){
        int count = 0;
        try {

            if ("add".equalsIgnoreCase(flag)) {
                //添加角色表 返回主键
                count =sysRoleService.insertSelective(sysRole);
                if (count > 0){
                    //根据 roleID 和 menuIDs 添加
                    insertRoleMentBatch(sysRole.getId(),ids);
                }
            }else if ("update".equalsIgnoreCase(flag)){
                //修改
                count = sysRoleService.updateByPrimaryKeySelective(sysRole);
                if (count > 0){
                    //先删除role_menu的表数据
                    sysRoleService.deleteRoleMenu(sysRole.getId());
                    //根据 roleID 和 menuIDs 添加
                    insertRoleMentBatch(sysRole.getId(),ids);
                }
            }else {
                //只修改 是否启动
                count = sysRoleService.updateByPrimaryKeySelective(sysRole);
            }
        }catch (Exception e){
            logger.error("save-=-:"+e.toString());
        }
        return count;
    }

    /**
     * 根据 roleID 和 menuIDs 添加
     * @param roleID
     * @param ids
     */
    public void insertRoleMentBatch(Integer roleID,Integer[] ids){
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<SysRoleMenu>();
        for (int i = 0; i < ids.length; i++) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(roleID);//取出主键
            sysRoleMenu.setMenuId(ids[i]);//循环添加对象
            sysRoleMenuList.add(sysRoleMenu);
        }
        if (sysRoleMenuList.size() > 0){
            //添加角色和菜单的关联表
            sysRoleService.insertRoleMenuBatch(sysRoleMenuList);
        }
    }

    /**
     * 根据id查询对象
     * @param id
     * @param request
     * @return
     */
    @RequiresPermissions("sys:role:update")
    @RequestMapping(method= RequestMethod.GET,value="/update",name = "修改角色页面")
    public ModelAndView roleUpd(Integer id, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        try {
            SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
            modelAndView.addObject("depList",sysDepartmentService.selectByPrimaryKeyForIdListRange());
            modelAndView.addObject("role",sysRole);
            modelAndView.setViewName("/sys/roleUpd");
        }catch (Exception e){
            logger.error("update-=-:"+e.toString());
        }
        return modelAndView;
    }

    /**
     * 批量删除role
     * @param ids
     * @return
     */
    @RequiresPermissions("sys:role:delete")
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/batchDelete",name = "批量删除角色")
    public int delete(@RequestParam(value = "ids[]")  Integer[]  ids){
        int count = 0;
        try {
            //级联删除   删除sys_role表的同时  也删除sys_role_menu 的表数据
            count = sysRoleService.deleteByids(ids);
        }catch (Exception e){
            logger.error("delete-=-:"+e.toString());
        }
        return count;
    }



    /**
     * 删除role
     * @param id
     * @return
     */
    @RequiresPermissions("sys:role:delete")
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/delete",name = "删除角色")
    public int delete(@RequestParam(value = "id")  Integer  id){
        int count = 0;
        try {
            //级联删除   删除sys_role表的同时  也删除sys_role_menu 的表数据
            count = sysRoleService.deleyeById(id);
        }catch (Exception e){
            logger.error("delete-=-:"+e.toString());
        }
        return count;
    }
}
