package com.dcy.listener;

import com.dcy.model.SysLog;
import com.dcy.model.SysUser;
import com.dcy.service.SysLogService;
import com.dcy.utils.UserUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/12.
 */
public class MyInterceptors implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(MyInterceptors.class);
    @Resource
    private SysLogService sysLogService;
    /**
     * 该方法在目标方法调用之后，渲染视图之前被调用；
     * 可以对请求域中的属性或视图做出修改
     *
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*logger.info("" + request.getRequestURI());*/
        if(!HandlerMethod.class.isAssignableFrom(handler.getClass())){
            return true;
        }
        SysUser sysUser = UserUtils.getUser();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        /*StringBuilder sb = new StringBuilder(1000);*/
        RequestMapping requestMapping = ((HandlerMethod) handler).getMethodAnnotation(RequestMapping.class);
        /*sb.append("ControName: ").append(requestMapping.name()).append("\n");
        sb.append("Controller: ").append(handlerMethod.getBean().getClass().getName()).append("\n");
        sb.append("Method    : ").append(handlerMethod.getMethod().getName()).append("\n");
        sb.append("Type    : ").append(request.getMethod()).append("\n");
        sb.append("Params    : ").append(getParamString(request.getParameterMap())).append("\n");
        sb.append("URI       : ").append(request.getRequestURI()).append("\n");
        sb.append("IP        :").append(getIpAddress(request)).append("\n");
        sb.append("Port        :").append(request.getServerPort()).append("\n");
        sb.append("user      :").append(sysUser.getUsername()).append("\n");
        sb.append("deparmentName :").append(sysUser.getDepartmentname()).append("\n");*/
        /*System.out.println(sb.toString());
        System.out.println("-----=-===========================================");*/

        SysLog sysLog = new SysLog(sysUser.getUsername(),sysUser.getDepartmentid(),requestMapping.name(),handlerMethod.getBean().getClass().getName(),
                handlerMethod.getMethod().getName(),request.getMethod(),getParamString(request.getParameterMap()),
                request.getRequestURI(),getIpAddress(request),request.getServerPort(),new Date());
        sysLogService.insertSelective(sysLog);
        return true;
    }

    /**
     * 该方法在目标方法调用之后，渲染视图之前被调用；
     * 可以对请求域中的属性或视图做出修改
     *
     */
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //System.out.println("MyInterceptors afterCompletion");
    }

    /**
     * 在渲染视图之后被调用；
     * 可以用来释放资源
     */
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //System.out.println("MyInterceptors afterCompletion");
    }


    /**
     * 获取请求的参数
     * @param map
     * @return
     */
    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String[]> e:map.entrySet()){
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if(value != null && value.length == 1){
                //sb.append(value[0]).append("\t");
                sb.append(value[0]).append("&");
            }else{
                //sb.append(Arrays.toString(value)).append("\t");
                sb.append(Arrays.toString(value)).append("&");
            }
        }
        if (sb.length() > 0){
            return sb.substring(0,sb.length()-1).toString();
        }
        return sb.toString();
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 参考文章： http://developer.51cto.com/art/201111/305181.htm
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
