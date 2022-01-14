package com.yt.seckill.controller;


import com.yt.seckill.entity.Dto.ParamUserDto;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}