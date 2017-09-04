package com.dcy.model;

import java.util.Date;

public class SysUser {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 部门
     */
    private Integer departmentid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 账户是否锁定
     */
    private Boolean locked;

    /**
     * 姓名
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 用户头像
     */
    private String photo;

    /**
     * 0 全部数据，1 自己部门数据，2 自己和直属下级数据，3 自己下的所有数据
     */
    private Integer datarange;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 默认1 男  2 女
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;


    /**
     * 主键
     * @return id 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 部门
     * @return departmentID 部门
     */
    public Integer getDepartmentid() {
        return departmentid;
    }

    /**
     * 部门
     * @param departmentid 部门
     */
    public void setDepartmentid(Integer departmentid) {
        this.departmentid = departmentid;
    }

    /**
     * 用户名
     * @return username 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 盐
     * @return salt 盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 盐
     * @param salt 盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 账户是否锁定
     * @return locked 账户是否锁定
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * 账户是否锁定
     * @param locked 账户是否锁定
     */
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 邮箱
     * @return email 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮箱
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 手机
     * @return phone 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 手机
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 用户头像
     * @return photo 用户头像
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 用户头像
     * @param photo 用户头像
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * 0 全部数据，1 自己部门数据，2 自己和直属下级数据，3 自己下的所有数据
     * @return dataRange 0 全部数据，1 自己部门数据，2 自己和直属下级数据，3 自己下的所有数据
     */
    public Integer getDatarange() {
        return datarange;
    }

    /**
     * 0 全部数据，1 自己部门数据，2 自己和直属下级数据，3 自己下的所有数据
     * @param datarange 0 全部数据，1 自己部门数据，2 自己和直属下级数据，3 自己下的所有数据
     */
    public void setDatarange(Integer datarange) {
        this.datarange = datarange;
    }

    /**
     * 最后登陆IP
     * @return login_ip 最后登陆IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 最后登陆IP
     * @param loginIp 最后登陆IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 最后登陆时间
     * @return login_date 最后登陆时间
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * 最后登陆时间
     * @param loginDate 最后登陆时间
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 备注信息
     * @return remarks 备注信息
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 备注信息
     * @param remarks 备注信息
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * 默认1 男  2 女
     * @return sex 默认1 男  2 女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 默认1 男  2 女
     * @param sex 默认1 男  2 女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 生日
     * @return birthday 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 生日
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }
}