package com.dcy.service;

import com.dcy.model.SysDict;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */
public interface SysDictService {

    /**
     * 根据类型查询字典
     * @param type
     * @return
     */
    List<SysDict> selectListByType(String type);
}
