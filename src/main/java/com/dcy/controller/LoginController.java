package com.dcy.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dcy.model.SysUser;
import com.dcy.service.SysUserService;



@Controller
@RequestMapping(value = "${adminPath}")
public class LoginController extends BaseController {
	
	private Logger logger = Logger.getLogger(LoginController.class);

    /**
     * 首页
     * @return
     */
	@RequestMapping(method = RequestMethod.GET,value = "/index",name = "首页")
    public String index(){
		//logger.info("首页");
        return "/index";
    }

    /**
     * 权限不足
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/unauthorized",name = "权限不足")
    public String unauthorized(){
        //logger.info("权限不足");
        return "/unauthorized";
    }
    /**
     * 欢迎页
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,value = "/welcome",name = "欢迎页")
    public String welcome(){
        //logger.info("欢迎页");
        return "/welcome";
    }

    /**
     * 登陆页面
     * @return
     */
	@RequestMapping(method=RequestMethod.GET,value = "/login",name = "登陆页面")
    public String login(){
		//logger.info("登陆  Get");
        //如果登陆  直接跳转首页
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipals() != null){
            return "/index";
        }
        return "/login";
    }
	
	/**
	 * 登陆
	 * @param request
	 * @param attr
	 * @param model
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST, value = "/login",name = "登陆")
	public String login(HttpServletRequest request,RedirectAttributes attr,Model model) {
		//logger.info("登陆  Post");
		//如果登陆失败从request中获取认证异常信息，shiroLoginFailure就是shiro异常类的全限定名
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        
        //根据shiro返回的异常类路径判断，抛出指定异常信息
        if(exceptionClassName!=null){
            String authticationError = null;
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                authticationError = "用户名/密码错误";
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                authticationError = "用户名/密码错误";
            }else if (ExcessiveAttemptsException.class.getName().equals(
                    exceptionClassName)) {
                authticationError = "登录失败多次，账户锁定10分钟";
            }else if (LockedAccountException.class.getName().equals(
                    exceptionClassName)) {
                authticationError = "帐号已锁定，请联系管理员";
            } else if (exceptionClassName != null) {
                //authticationError = "未知错误：" + exceptionClassName;
                authticationError = "用户名/密码错误";
            }
            model.addAttribute("authticationError", authticationError);
        }
        //此方法不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径
        //登陆失败还到login页面
        return "/login";
	}

}
