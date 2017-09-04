package com.dcy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
public class MenuTreeNode {
    private int id;
    private int parentId;
    private boolean isOpen;
    private String text;
    private boolean isHeader;
    private String icon;
    private List<MenuTreeNode> children = new ArrayList<MenuTreeNode>();
    private String url;
    private String targetType;

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty(value = "isOpen")
    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty(value = "isHeader")
    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeNode> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public MenuTreeNode(int id, int parentId, boolean isOpen, String text, boolean isHeader, String icon, String url, String targetType) {
        this.id = id;
        this.parentId = parentId;
        this.isOpen = isOpen;
        this.text = text;
        this.isHeader = isHeader;
        this.icon = icon;
        this.url = url;
        this.targetType = targetType;
    }

    public void add(MenuTreeNode menuTreeNode) {//递归添加节点
        if ("0".equals(menuTreeNode.getParentId())) {
            this.children.add(menuTreeNode);
        } else if (menuTreeNode.getParentId() == this.getId()) {
            this.children.add(menuTreeNode);
        } else {
            for (MenuTreeNode menuTreeNode2 : children) {
                menuTreeNode2.add(menuTreeNode);
            }
        }
    }


}
