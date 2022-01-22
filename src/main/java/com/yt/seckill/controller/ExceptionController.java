package com.yt.seckill.controller;

import com.yt.seckill.Response.ResultData;
import com.yt.seckill.Response.ReturnCode;
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
     * 重新抛出异常(改为直接返回错误信息)
     */
    @RequestMapping("/filterExceptionOnLoginFailure")
    public ResultData<String> filterExceptionOnLoginFailure(HttpServletRequest request) throws Exception {
//        返回的是java.lang.RuntimeException: 请重新登录，分割一下只输出信息
//        throw new RuntimeException(request.getAttribute("errMsg").toString().split(":")[1]);
        return ResultData.fail(ReturnCode.FAILD_LGOGIN.getCode(),ReturnCode.FAILD_LGOGIN.getMessage());

    }
    /**
     * 重新抛出异常(改为直接返回错误信息)
     */
    @RequestMapping("/filterExceptionOnAccessDenied")//token不存在
    public ResultData<String> filterExceptionOnAccessDenied(HttpServletRequest request) throws Exception {
//        返回的是java.lang.RuntimeException: 请重新登录，分割一下只输出信息
//        throw new RuntimeException(request.getAttribute("errMsg").toString().split(":")[1]);
        return ResultData.fail(ReturnCode.INVALID_TOKEN.getCode(),ReturnCode.INVALID_TOKEN.getMessage());

    }
}
