package com.dcy.model;

public class SysDepartment {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父级编号
     */
    private Integer parentid;

    /**
     * 所有父级编号
     */
    private String parentids;

    /**
     * 默认值 0 正常，1 已经删除的
     */
    private Boolean state;

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
     * 部门名称
     * @return name 部门名称
     */
    public String getName() {
        return name;
    }

    /**
     * 部门名称
     * @param name 部门名称
     */
    public void setName(String name) {
        this.name = name;
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
     * 默认值 0 正常，1 已经删除的
     * @return state 默认值 0 正常，1 已经删除的
     */
    public Boolean getState() {
        return state;
    }

    /**
     * 默认值 0 正常，1 已经删除的
     * @param state 默认值 0 正常，1 已经删除的
     */
    public void setState(Boolean state) {
        this.state = state;
    }
}