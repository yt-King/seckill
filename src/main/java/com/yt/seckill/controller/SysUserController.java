package com.yt.seckill.controller;


import com.alibaba.fastjson.JSONObject;
import com.yt.seckill.entity.Dto.ParamUserDto;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TGoods;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

import static com.yt.seckill.utils.MD5Utils.string2MD5;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-11-20
 */
@RestController
@RequestMapping("/sysuser")
@Tag(name = "sys-user-controller", description = "用户接口")
public class SysUserController {
    @Autowired
    private SysUserServiceImpl sysUserService;

    @PostMapping("/reg")
    @Operation(summary = "用户注册")
    public Map insert(@RequestBody @Valid SysUser entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return sysUserService.insertUser(entity);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Map login(@RequestBody @Valid ParamUserDto entity, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return sysUserService.login(entity, request, response);
    }

    @PostMapping("/list")
    @Operation(summary = "分页+条件查询用户列表，参数：根据用户名或账号（手机号）查询")
    public Map list(@RequestBody Map params) {
        return sysUserService.selectlist(params);
    }

    @PostMapping("/update")
    @Operation(summary = "更新操作，根据user_id更新")
    public String update(@RequestBody SysUser entity) {
        return sysUserService.updateOne(entity);
    }

    @PostMapping("/detail")
    @Operation(summary = "根据tel查询对应用户信息")
    public SysUser detail(@RequestBody String userId) {
        return sysUserService.detail((String) JSONObject.parseObject(userId).get("userId"));
    }

    @PostMapping("/delete")
    @Operation(summary = "用户删除")
    public String delete(@RequestBody String userId) {
        return sysUserService.deleteUser((String) JSONObject.parseObject(userId).get("userId"));
    }
}