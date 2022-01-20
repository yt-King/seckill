package com.yt.seckill.shiro;


import com.yt.seckill.entity.SysUser;
import com.yt.seckill.mapper.SysUserMapper;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;

import static com.yt.seckill.utils.MD5Utils.string2MD5;

/**
 * @author 应涛
 * @date 2022/1/18
 * @function：
 */

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    RedisTemplate redisTemplate;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<Map<String, Object>> powerList = sysUserService.getUserPower(sysUser.getUserId());
        System.out.println(powerList.toString());
        for (Map<String, Object> powerMap : powerList) {
            //添加角色
            simpleAuthorizationInfo.addRole(String.valueOf(powerMap.get("roleName")));
            //添加权限
            simpleAuthorizationInfo.addStringPermission(String.valueOf(powerMap.get("permissionsName")));
        }
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取token信息
        String accessToken  = authenticationToken.getPrincipal().toString();
        //根据token获取用户信息
        SysUser sysUser = (SysUser) redisTemplate.opsForValue().get(accessToken);

        if (sysUser == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser, accessToken , getName());
            return simpleAuthenticationInfo;
        }
    }
}