package com.dcy.service;

import com.dcy.model.SysUser;
import com.dcy.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/9/8.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(BaseService.class);


    /**
     * 数据过滤
     * @param sysUser
     * @param depAlias  部门表的别名
     * @return
     */
    public static String dataScopeFilter(SysUser sysUser, String depAlias) {
        StringBuilder sqlString = new StringBuilder();
        //判断是不是管理员
        if (sysUser.isAdmin()){
            return "";
        }
        switch (sysUser.getDatarange()){
            case SysUser.DATA_SCOPE_ALL://所有数据
                break;
            case SysUser.DATA_SCOPE_OFFICE://所在部门数据
                sqlString.append(" AND "+depAlias+".departmentID = "+sysUser.getDepartmentid()+" ");
                break;
            case SysUser.DATA_SCOPE_OFFICE_AND_CHILD://所在部门和直属下级数据
                sqlString.append(" AND "+depAlias+".departmentID in ("+ UserUtils.getDepIds(sysUser.getDepartmentid())+") ");
                break;
            case SysUser.DATA_SCOPE_OFFICE_AND_ALL://所在部门下的所有数据
                sqlString.append(" AND "+depAlias+".departmentID in ("+ UserUtils.getDepIdsALL(sysUser.getDepartmentid())+") ");
                break;
            default:
                break;
        }

        return sqlString.toString();
    }
}
