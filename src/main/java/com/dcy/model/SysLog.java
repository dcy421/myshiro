package com.dcy.model;

import java.util.Date;

public class SysLog {
    /**
     * 
     */
    private Integer id;

    /**
     * 请求人
     */
    private String username;

    /**
     * 
     */
    private Integer departmentid;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String controller;

    /**
     * 
     */
    private String method;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String params;

    /**
     * 
     */
    private String requesturi;

    /**
     * 
     */
    private String ip;

    /**
     * 
     */
    private Integer port;

    /**
     * 
     */
    private Date date;



    private String sql;


    private String departmentname;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 请求人
     * @return username 请求人
     */
    public String getUsername() {
        return username;
    }

    /**
     * 请求人
     * @param username 请求人
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return departmentid 
     */
    public Integer getDepartmentid() {
        return departmentid;
    }

    /**
     * 
     * @param departmentid 
     */
    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    /**
     * 
     * @return title 
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return controller 
     */
    public String getController() {
        return controller;
    }

    /**
     * 
     * @param controller 
     */
    public void setController(String controller) {
        this.controller = controller;
    }

    /**
     * 
     * @return method 
     */
    public String getMethod() {
        return method;
    }

    /**
     * 
     * @param method 
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 
     * @return type 
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type 
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return params 
     */
    public String getParams() {
        return params;
    }

    /**
     * 
     * @param params 
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 
     * @return requestURI 
     */
    public String getRequesturi() {
        return requesturi;
    }

    /**
     * 
     * @param requesturi 
     */
    public void setRequesturi(String requesturi) {
        this.requesturi = requesturi;
    }

    /**
     * 
     * @return ip 
     */
    public String getIp() {
        return ip;
    }

    /**
     * 
     * @param ip 
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 
     * @return port 
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 
     * @param port 
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * 
     * @return date 
     */
    public Date getDate() {
        return date;
    }

    /**
     * 
     * @param date 
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public SysLog(String username, Integer departmentid, String title, String controller, String method, String type, String params, String requesturi, String ip, Integer port, Date date) {
        this.username = username;
        this.departmentid = departmentid;
        this.title = title;
        this.controller = controller;
        this.method = method;
        this.type = type;
        this.params = params;
        this.requesturi = requesturi;
        this.ip = ip;
        this.port = port;
        this.date = date;
    }

    public SysLog() {
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

}