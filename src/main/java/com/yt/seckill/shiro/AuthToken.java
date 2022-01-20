package com.yt.seckill.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 应涛
 * @date 2022/1/18
 * @function：自定义AuthenticationToken类
 */

public class AuthToken extends UsernamePasswordToken {

    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}