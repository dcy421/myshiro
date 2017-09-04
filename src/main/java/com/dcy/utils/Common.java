package com.dcy.utils;


import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 公共工具类
 * Created by Administrator on 2017/9/4.
 */
public class Common {

    private Logger logger = Logger.getLogger(Common.class);
    /**
     * bootstrap table 表格封装
     * @param count
     * @param list
     * @return
     */
    public static Map getBSTData(int count, List list){
        Map map = new HashMap();
        try {
            map.put("total",count);
            map.put("rows",list);
        }catch (Exception ex){

        }
        return map;
    }
}
