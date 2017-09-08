package com.dcy.utils;

import com.dcy.dao.SysDepartmentMapper;
import com.dcy.dao.SysMenuMapper;
import com.dcy.dao.SysRoleMapper;
import com.dcy.dao.SysUserMapper;
import com.dcy.model.SysDepartment;
import com.dcy.model.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 用户工具类
 * Created by Administrator on 2017/9/8.
 */
public class UserUtils {
    private static SysUserMapper sysUserMapper = SpringContextHolder.getBean(SysUserMapper.class);
    private static SysRoleMapper sysRoleMapper = SpringContextHolder.getBean(SysRoleMapper.class);
    private static SysDepartmentMapper sysDepartmentMapper = SpringContextHolder.getBean(SysDepartmentMapper.class);
    private static SysMenuMapper sysMenuMapper = SpringContextHolder.getBean(SysMenuMapper.class);

    public static final String USER_CACHE = "userCache";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "ln";
    public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

    public static final String CACHE_AUTH_INFO = "authInfo";
    public static final String CACHE_ROLE_LIST = "roleList";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String CACHE_AREA_LIST = "areaList";
    public static final String CACHE_OFFICE_LIST = "officeList";
    public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

    public static final String CACHE_DEPID = "depids";


    /**
     * 根据ID获取用户
     * @param id
     * @return 取不到返回null
     */
    public static SysUser get(Integer id){
        SysUser sysUser = (SysUser)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (sysUser ==  null){
            sysUser = sysUserMapper.selectByPrimaryKey(id);
            if (sysUser == null){
                return null;
            }
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + sysUser.getId(), sysUser);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + sysUser.getUsername(), sysUser);
        }
        return sysUser;
    }


    /**
     * 根据登录名获取用户
     * @param loginName
     * @return 取不到返回null
     */
    public static SysUser getByLoginName(String loginName){
        SysUser sysUser = (SysUser)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (sysUser == null){
            sysUser = sysUserMapper.selectByUserName(loginName);
            if (sysUser == null){
                return null;
            }
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + sysUser.getId(), sysUser);
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + sysUser.getUsername(), sysUser);
        }
        return sysUser;
    }

    /**
     * 根据部门id查询看的所在部门   自己下的所有部门
     * @param id
     * @return
     */
    public static String getDepIdsALL(Integer id){
        String s = "";
        List<Map> sysDepartmentList = (List<Map>) CacheUtils.get(USER_CACHE, CACHE_DEPID + id);
        if (sysDepartmentList == null){
            sysDepartmentList = sysDepartmentMapper.selectByPrimaryKeyForList(id);
            if (sysDepartmentList == null){
                return "";
            }
            for (Map map:sysDepartmentList){
                s += map.get("id")+",";
            }
            if (s.length() > 0){
                s = s.substring(0,s.length()-1);
            }
            CacheUtils.put(USER_CACHE, CACHE_DEPID + id, s);
        }
        return s;
    }

    /**
     * 根据部门id 查询自己和直属下级的数据
     * @param id
     * @return
     */
    public static String getDepIds(Integer id){
        String s = "";
        List<Integer> integerList = (List<Integer>) CacheUtils.get(USER_CACHE, CACHE_DEPID + id);
        if (integerList == null){
            integerList = sysDepartmentMapper.selectByPrimaryKeyForIdList(id);
            if (integerList == null){
                return "";
            }
            for (Integer integer:integerList){
                s += integer+",";
            }
            if (s.length() > 0){
                s = s.substring(0,s.length()-1);
            }
            CacheUtils.put(USER_CACHE, CACHE_DEPID + id, s);
        }
        return s;
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    /**
     * 清除当前用户缓存
     */
    public static void clearCache(){
        removeCache(CACHE_AUTH_INFO);
        removeCache(CACHE_ROLE_LIST);
        removeCache(CACHE_MENU_LIST);
        removeCache(CACHE_AREA_LIST);
        removeCache(CACHE_OFFICE_LIST);
        removeCache(CACHE_OFFICE_ALL_LIST);
        UserUtils.clearCache(getUser());
    }

    /**
     * 获取当前用户
     * @return 取不到返回 new User()
     */
    public static SysUser getUser(){
        SysUser user = getByLoginName(getPrincipal());
        if (user != null){
            return user;
        }
        return new SysUser();
    }

    /**
     * 获取当前登录者对象
     */
    public static String getPrincipal(){
        try{
            Subject subject = SecurityUtils.getSubject();
            String loginName = (String) subject.getPrincipal();
            if (loginName != null){
                return loginName;
            }
//			subject.logout();
        }catch (UnavailableSecurityManagerException e) {

        }catch (InvalidSessionException e){

        }
        return null;
    }

    /**
     * 清除指定用户缓存
     * @param user
     */
    public static void clearCache(SysUser user){
        CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
        CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getUsername());
    }



    public static Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
//			subject.logout();
        }catch (InvalidSessionException e){

        }
        return null;
    }


    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
        Object obj = getSession().getAttribute(key);
        return obj==null?defaultValue:obj;
    }

    public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
//		getCacheMap().remove(key);
        getSession().removeAttribute(key);
    }
}
