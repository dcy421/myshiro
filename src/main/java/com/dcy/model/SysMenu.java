package com.dcy.model;

import java.util.Date;

public class SysMenu {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 父级编号
     */
    private Integer parentid;

    /**
     * 所有父级编号
     */
    private String parentids;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Date sort;

    /**
     * 链接
     */
    private String href;

    /**
     * 目标
     */
    private String target;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否在菜单中显示
     */
    private Boolean available;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 备注信息
     */
    private String remarks;

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
     * 父级编号
     * @return parentID 父级编号
     */
    public Integer getParentid() {
        return parentid;
    }

    /**
     * 父级编号
     * @param parentid 父级编号
     */
    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    /**
     * 所有父级编号
     * @return parentIDs 所有父级编号
     */
    public String getParentids() {
        return parentids;
    }

    /**
     * 所有父级编号
     * @param parentids 所有父级编号
     */
    public void setParentids(String parentids) {
        this.parentids = parentids;
    }

    /**
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 排序
     * @return sort 排序
     */
    public Date getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(Date sort) {
        this.sort = sort;
    }

    /**
     * 链接
     * @return href 链接
     */
    public String getHref() {
        return href;
    }

    /**
     * 链接
     * @param href 链接
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * 目标
     * @return target 目标
     */
    public String getTarget() {
        return target;
    }

    /**
     * 目标
     * @param target 目标
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 图标
     * @return icon 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 图标
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 是否在菜单中显示
     * @return available 是否在菜单中显示
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * 是否在菜单中显示
     * @param available 是否在菜单中显示
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * 权限标识
     * @return permission 权限标识
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 权限标识
     * @param permission 权限标识
     */
    public void setPermission(String permission) {
        this.permission = permission;
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
}