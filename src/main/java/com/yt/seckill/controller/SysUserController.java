package com.yt.seckill.controller;


import com.yt.seckill.entity.SysUser;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-11-20
 */
@RestController
@RequestMapping("/sysuser")
public class SysUserController {
    @Autowired
    private SysUserServiceImpl sysUserService;
    @PostMapping("/reg")
    public Map insert(@RequestBody @Valid SysUser entity, BindingResult bindingResult)  {
        if(bindingResult.hasErrors()){
            Map map =new HashMap();
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        return sysUserService.insertUser(entity);
    }
}