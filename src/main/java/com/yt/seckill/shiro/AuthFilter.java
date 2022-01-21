package com.yt.seckill.shiro;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.utils.TokenUtil;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应涛
 * @date 2022/1/18
 * @function：
 */
@Component
public class AuthFilter extends AuthenticatingFilter {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 生成自定义token
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token(dataId)
        String token = TokenUtil.getRequestToken((HttpServletRequest) request);

        return new AuthToken(token);
    }

    /**
     * 步骤1.所有请求全部拒绝访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return false;
    }

    /**
     * 步骤2，拒绝访问的请求，会调用onAccessDenied方法，onAccessDenied方法先获取 token，再调用executeLogin方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回
        String token = TokenUtil.getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            RuntimeException runtimeException = new RuntimeException("token不存在");
            // 异常捕获，发送到expiredJwtException
            request.setAttribute("errMsg", runtimeException);
            //将异常分发到/filterException控制器
            request.getRequestDispatcher("/filterException").forward(request, response);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 重写认证失败的函数像controller抛出异常，不然不会返回结果
     *
     */
    @SneakyThrows
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response) {
        RuntimeException runtimeException = new RuntimeException("登陆状态已失效，请重新登录");
        // 异常捕获，发送到expiredJwtException
        request.setAttribute("errMsg", runtimeException);
        //将异常分发到/filterException控制器
        request.getRequestDispatcher("/filterException").forward(request, response);
        return false;
    }

}