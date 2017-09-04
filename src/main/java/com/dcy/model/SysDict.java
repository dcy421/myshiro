package com.dcy.model;

public class SysDict {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 数据值
     */
    private String code;

    /**
     * 标签名
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private Long sort;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 是否可用 默认0   1已经删除
     */
    private Boolean available;

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
     * 数据值
     * @return code 数据值
     */
    public String getCode() {
        return code;
    }

    /**
     * 数据值
     * @param code 数据值
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 标签名
     * @return name 标签名
     */
    public String getName() {
        return name;
    }

    /**
     * 标签名
     * @param name 标签名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 类型
     * @return type 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 类型
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 描述
     * @return description 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 描述
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 排序（升序）
     * @return sort 排序（升序）
     */
    public Long getSort() {
        return sort;
    }

    /**
     * 排序（升序）
     * @param sort 排序（升序）
     */
    public void setSort(Long sort) {
        this.sort = sort;
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
     * 是否可用 默认0   1已经删除
     * @return available 是否可用 默认0   1已经删除
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * 是否可用 默认0   1已经删除
     * @param available 是否可用 默认0   1已经删除
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }
}