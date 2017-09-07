package com.dcy.shiro.realm;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Repository;

import com.dcy.model.SysUser;
import com.dcy.service.SysUserService;

import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRealm extends AuthorizingRealm {

	private Logger logger = Logger.getLogger(UserRealm.class);
	@Resource
	private SysUserService sysUserService;
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//logger.info(" 授权 ");
		//获取登录名
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//获取登陆人有哪些角色
		authorizationInfo.setRoles(sysUserService.getRoleNameByUserName(username));
		//获取登陆人有哪些权限
		authorizationInfo.setStringPermissions(sysUserService.getPermissionsByUserName(username));
		return authorizationInfo;
	}

	
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//logger.info(" 认证 ");
		//token中储存着输入的用户名和密码
        //UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        String userName = (String) token.getPrincipal();
        /*logger.info(" 登录名"+upToken.getUsername());
        logger.info(" 密码名"+String.valueOf(upToken.getPassword()));*/
        //根据登录名查询对象
        SysUser sysUser = sysUserService.selectByUserName(userName);
        if (sysUser == null) {
        	throw new UnknownAccountException();//没找到帐号
		}
        if (Boolean.TRUE.equals(sysUser.getLocked())) {
        	throw new LockedAccountException(); //帐号锁定
		}
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		sysUser.getUsername(), //用户名
        		sysUser.getPassword(), //密码
                ByteSource.Util.bytes(sysUser.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
	}

	/**
	 * 清空之前  授权   缓存的AuthorizationInfo
	 */
	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

	/**
	 * 清空之前  认证  缓存的AuthenticationInfo
	 */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

	/**
	 * 授权
	 */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

	/**
	 * 认证
	 */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

	/**
	 * 清除全部缓存
	 */
	public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
    
}
