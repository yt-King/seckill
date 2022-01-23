package com.yt.seckill.utils;

import java.util.Map;

/**
 * @author 应涛
 * @date 2022/1/23
 * @function：
 */
public class PageUtils {
    public static void checkPage(Map params) {
        if(null==params.get("pageNum"))
            params.put("pageNum",0);
        if(null==params.get("pageSize"))
            params.put("pageSize",10);
    }
}
