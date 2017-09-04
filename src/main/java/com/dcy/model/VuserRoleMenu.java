package com.dcy.model;

import java.util.ArrayList;
import java.util.List;

public class VuserRoleMenu {

    /**
     * 
     */
    private Integer menuid;

    /**
     * 上级
     */
    private Integer parentId;

    /**
     * 所有的上级
     */
    private String parentIds;

    /**
     * 名称
     */
    private String menuname;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 链接地址
     */
    private String href;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否显示   0显示  1不显示
     */
    private Integer isShow;

    /**
     * 权限标识
     */
    private String permission;

    private List<VuserRoleMenu> children = new ArrayList<VuserRoleMenu>();


    /**
     * 目标目标（mainFrame、 _blank、_self、_parent、_top）
     */
    private String target;

    public void add(VuserRoleMenu vuserRoleMenu) {//递归添加节点
        if ("0".equals(vuserRoleMenu.getParentId())) {
            this.children.add(vuserRoleMenu);
        } else if (vuserRoleMenu.getParentId().equals(this.getMenuid())) {
            this.children.add(vuserRoleMenu);
        } else {
            for (VuserRoleMenu VuserRoleMenu : children) {
                VuserRoleMenu.add(vuserRoleMenu);
            }
        }
    }


    public void setChildren(List<VuserRoleMenu> children) {
        this.children = children;
    }
    public List<VuserRoleMenu> getChildren() {
        return children;
    }

    /**
     * 
     * @return menuID 
     */
    public Integer getMenuid() {
        return menuid;
    }

    /**
     * 
     * @param menuid 
     */
    public void setMenuid(Integer menuid) {
        this.menuid = menuid;
    }

    /**
     * 上级
     * @return parent_id 上级
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 上级
     * @param parentId 上级
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 所有的上级
     * @return parent_ids 所有的上级
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * 所有的上级
     * @param parentIds 所有的上级
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    /**
     * 名称
     * @return menuName 名称
     */
    public String getMenuname() {
        return menuname;
    }

    /**
     * 名称
     * @param menuname 名称
     */
    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    /**
     * 排序
     * @return sort 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 链接地址
     * @return href 链接地址
     */
    public String getHref() {
        return href;
    }

    /**
     * 链接地址
     * @param href 链接地址
     */
    public void setHref(String href) {
        this.href = href;
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
     * 是否显示   0显示  1不显示
     * @return is_show 是否显示   0显示  1不显示
     */
    public Integer getIsShow() {
        return isShow;
    }

    /**
     * 是否显示   0显示  1不显示
     * @param isShow 是否显示   0显示  1不显示
     */
    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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
     * 目标目标（mainFrame、 _blank、_self、_parent、_top）
     * @return target 目标目标（mainFrame、 _blank、_self、_parent、_top）
     */
    public String getTarget() {
        return target;
    }

    /**
     * 目标目标（mainFrame、 _blank、_self、_parent、_top）
     * @param target 目标目标（mainFrame、 _blank、_self、_parent、_top）
     */
    public void setTarget(String target) {
        this.target = target;
    }
}