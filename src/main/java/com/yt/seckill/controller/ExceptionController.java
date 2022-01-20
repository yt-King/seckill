package com.yt.seckill.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应涛
 * @date 2022/1/19
 * @function： 过滤器异常重定向抛出用于被全局异常处理器捕获
 */
@RestController
public class ExceptionController {
    /**
     * 重新抛出异常
     */
    @RequestMapping("/filterException")
    public void filterException(HttpServletRequest request) throws Exception {
        //返回的是java.lang.RuntimeException: 请重新登录，分割一下只输出信息
        throw new RuntimeException(request.getAttribute("errMsg").toString().split(":")[1]);
    }
}
