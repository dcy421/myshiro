package com.dcy.controller;

import com.dcy.model.BootStrapTable;
import com.dcy.model.SysRole;
import com.dcy.model.SysRoleMenu;
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
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysDictService sysDictService;

    /**
     * 跳转页面
     * @param request
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:search")
    @RequestMapping("/index")
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
    @RequestMapping(method= RequestMethod.POST,value="/getRolePageList")
    public Map getRolePageList(BootStrapTable bootStrapTable, SysRole sysRole){
        Map map = new HashMap();
        try{
            map = new Common().getBSTData(sysRoleService.getRoleCount(sysRole), sysRoleService.getRolePageList(bootStrapTable,sysRole));
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
    @RequestMapping("/add")
    public String roleAdd(HttpServletRequest request, Model model){
        return "/sys/roleAdd";
    }

    /**
     * 检测是否有这个账户
     * @param roleName
     * @return
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/getRepeatRoleName")
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
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public int SaveSysRole(SysRole sysRole,String flag,@RequestParam(value = "ids")  Integer[]  ids){
        int count = 0;
        List<SysRoleMenu> sysRoleMenuList = new ArrayList<SysRoleMenu>();
        try {

            if ("add".equalsIgnoreCase(flag)) {
                //添加角色表 返回主键
                count =sysRoleService.insertSelective(sysRole);
                if (count > 0){
                    for (int i = 0; i < ids.length; i++) {
                        SysRoleMenu sysRoleMenu = new SysRoleMenu();
                        sysRoleMenu.setRoleId(sysRole.getId());//取出主键自增之后的值
                        sysRoleMenu.setMenuId(ids[i]);//循环添加对象
                        sysRoleMenuList.add(sysRoleMenu);
                    }
                    if (sysRoleMenuList.size() > 0){
                        //添加角色和菜单的关联表
                        sysRoleService.insertRoleMenuBatch(sysRoleMenuList);
                    }
                }
            }else if ("update".equalsIgnoreCase(flag)){
                //修改
                count = sysRoleService.updateByPrimaryKeySelective(sysRole);
                if (count > 0){
                    //先删除role_menu的表数据
                    sysRoleService.deleteRoleMenu(sysRole.getId());
                    for (int i = 0; i < ids.length; i++) {
                        SysRoleMenu sysRoleMenu = new SysRoleMenu();
                        sysRoleMenu.setRoleId(sysRole.getId());//取出主键
                        sysRoleMenu.setMenuId(ids[i]);//循环添加对象
                        sysRoleMenuList.add(sysRoleMenu);
                    }
                    if (sysRoleMenuList.size() > 0){
                        //添加角色和菜单的关联表
                        sysRoleService.insertRoleMenuBatch(sysRoleMenuList);
                    }
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
     * 根据id查询对象
     * @param id
     * @param request
     * @return
     */
    @RequiresPermissions("sys:role:update")
    @RequestMapping(method= RequestMethod.GET,value="/update")
    public ModelAndView roleUpd(Integer id, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        try {
            SysRole sysRole = sysRoleService.selectByPrimaryKey(id);
            modelAndView.addObject("role",sysRole);
            modelAndView.setViewName("/sys/roleUpd");
        }catch (Exception e){
            logger.error("update-=-:"+e.toString());
        }
        return modelAndView;
    }

    /**
     * 删除role
     * @param ids
     * @return
     */
    @RequiresPermissions("sys:role:delete")
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/delete")
    public int delete(@RequestParam(value = "ids[]")  Integer[]  ids){
        int count = 0;
        try {
            //级联删除   删除sys_role表的同时  也删除sys_role_menu 的表数据
            count = sysRoleService.deleteByids(ids);
        }catch (Exception e){
            logger.error("DelSysRole-=-:"+e.toString());
        }
        return count;
    }
}
