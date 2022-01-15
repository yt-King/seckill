package com.yt.seckill.aspect;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 应涛
 * @date 2021/11/21
 * @function：
 */
@Aspect
@Component
@Slf4j
public class LoginAspect {
    @Autowired
    RedisTemplate redisTemplate;

    //定义一个切面,注意这里将用户操作这块的排除了,因为这里包含用户登陆登出,不需要校验.
    @Pointcut("execution(public * com.yt.seckill.controller.TGoodsController.*(..))")
    public void verify() {
    }


    @Before("verify()")
    public void doVerify() {
        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        //查询cookie,使用封装好的工具类
//        System.out.println("CookieUtils.getCookieValue(request,\"userTicket\") = " + CookieUtils.getCookieValue(request, "userTicket"));
        String userTicket = CookieUtils.getCookieValue(request, "userTicket");
        if (StringUtils.isEmpty(userTicket)) {
            throw new RuntimeException("您还未登录或登录状态已失效");
        }
        SysUser user = (SysUser) redisTemplate.opsForValue().get("user:" + userTicket);
        if (null == user) {
            throw new RuntimeException("您还未登录或登录状态已失效");
        }
//        CookieUtils.setCookie(request, response, "userTicket", userTicket);
    }
}
