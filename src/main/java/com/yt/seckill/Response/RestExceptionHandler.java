package com.yt.seckill.Response;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 应涛
 * @date 2022/1/12
 * @function：
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    /**
     * 权限异常处理。
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler
    public ResultData<String> ErrorHandler(AuthorizationException e) {
        return ResultData.fail(ReturnCode.RC500.getCode(),"您暂时没有权限,请联系管理员！");
    }
    /**
     * 默认全局异常处理。
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exception(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return ResultData.fail(ReturnCode.RC500.getCode(),e.getMessage());
    }

}
