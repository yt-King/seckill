package com.yt.seckill.controller;


import com.yt.seckill.entity.TSeckillGoods;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import com.yt.seckill.service.impl.TSeckillGoodsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
@RestController
@RequestMapping("/seckillgoods")
@Tag(name = "t-seckill-goods-controller", description = "秒杀商品接口")
public class TSeckillGoodsController {
    @Autowired
    TSeckillGoodsServiceImpl tSeckillGoodsService;
    @Autowired
    SysUserServiceImpl sysUserService;

    @PostMapping(value = "/gethash")
    @Operation(summary = "获取验证值")
    public String getVerifyHash(@RequestBody TSeckillGoods tSeckillGoods, HttpServletRequest request) throws Exception {
        //判断是否禁止访问
        sysUserService.CheckIpIsBanned(request);
        //增加ip访问次数
        sysUserService.addIpCount(request);
        //根据用户id和商品id获得验证值
        return tSeckillGoodsService.getVerifyHash(tSeckillGoods.getDataId());
    }
}
