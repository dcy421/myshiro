package com.dcy.controller;

/**
 * Created by Administrator on 2017/9/8.
 */

import com.dcy.model.SysDepartment;
import com.dcy.service.SysDepartmentService;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 部门控制器
 */
@Controller
@RequestMapping("${adminPath}/sys/depm")
public class DepmController {
    private Logger logger = Logger.getLogger(DepmController.class);

    @Resource
    private SysDepartmentService sysDepartmentService;
    /**
     * 跳转 人员首页
     * @param request
     * @param model
     * @return
     */
    /*@RequiresPermissions("sys:depm:search")*/
    @RequestMapping(method= RequestMethod.GET,value = {"/index"})
    public String index(HttpServletRequest request, Model model) {
        return "/sys/depmIndex";
    }


    /**
     * 根据部门id递归查询所有的部门数据
     * @param departmentID
     * @return
     */
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/getDepartmentList")
    public List<Map> getDepartmentList(Integer departmentID){
        departmentID = 1;
        //获取所有的数据
        List<Map> sysDepartments = sysDepartmentService.selectByPrimaryKeyForList(departmentID);
        return sysDepartments;
    }

    /**
     * 添加和修改
     * @param sysDepartment
     * @param flag
     * @return
     */
    @RequiresPermissions(value={"sys:depm:add", "sys:depm:update"}, logical= Logical.OR)
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public int save(SysDepartment sysDepartment, int flag){
        int count = 0;
        try {
            //添加
            if (flag == 0){
                count = sysDepartmentService.insertSelective(sysDepartment);
            }else {//修改
                count = sysDepartmentService.updateByPrimaryKeySelective(sysDepartment);
            }
        }catch (Exception e){
            logger.error("save-=-:"+e.toString());
        }
        return count;
    }


    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequiresPermissions(value={"sys:depm:delete"})
    @ResponseBody
    @RequestMapping(method= RequestMethod.POST,value="/delete")
    public int delete(Integer id){
        int count = 0;
        try {
            count = sysDepartmentService.deleteByPrimaryKey(id);
        }catch (Exception e){
            logger.error("saveDepartment-=-:"+e.toString());
        }
        return count;
    }
}
