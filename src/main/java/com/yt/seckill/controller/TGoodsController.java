package com.yt.seckill.controller;


import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TGoods;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import com.yt.seckill.service.impl.TGoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-01-12
 */
@RestController
@RequestMapping("/goods")
public class TGoodsController {
    @Autowired
    private TGoodsServiceImpl tGoodsService;

    @PostMapping("/insert")
    public Map insert(@RequestBody @Valid TGoods entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map map = new HashMap();
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        return tGoodsService.insertGoods(entity);
    }
}
