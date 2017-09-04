package com.dcy.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理类
 * @author Administrator
 *
 */
public class MyExceptionHandler implements HandlerExceptionResolver{

	
	private Logger logger = Logger.getLogger(MyExceptionHandler.class);
	
	
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// TODO Auto-generated method stub
		logger.error(ex.toString());
		//logger.error("==============异常开始=============");
		//System.out.println("==============异常开始=============");
		//System.out.println(ex.toString());
		ex.printStackTrace();
		//logger.error(ex.toString());
		//System.out.println("==============异常结束=============");
		//logger.error("==============异常结束=============");
		//ModelAndView mv = new ModelAndView("error");
		//mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);

		// 根据不同错误转向不同页面
		if(ex instanceof UnauthorizedException) {
			return new ModelAndView("unauthorized", model);
		}else if(ex instanceof HostUnauthorizedException) {
			return new ModelAndView("unauthorized", model);
		} else if (ex instanceof UnauthenticatedException){
			return new ModelAndView("unauthorized", model);
		} else if(ex instanceof AuthorizationException){
			return new ModelAndView("unauthorized", model);
		}else {
			return new ModelAndView("error", model);
		}

	}
	
	
}
