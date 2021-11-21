package com.yt.seckill.controller;


import com.yt.seckill.error.RrException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * controller 增强器
 */
@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RrException.class)
    public Map errorHandler(RrException ex) {
        Map map = new HashMap();
        map.put("code",500);
        map.put("msg",ex.getMsg());
        return map;
    }

}