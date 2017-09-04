package com.dcy.model;

/**
 * Created by Administrator on 2017/6/19.
 */
public class BootStrapTable {
    private int limit;//每页多少条
    private int offset;//多少开始
    private String search;//查询数据
    private String sort;//排序字段
    private String order;//排序类型

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
