package com.yt.seckill.controller;


import com.alibaba.fastjson.JSONObject;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TGoods;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import com.yt.seckill.service.impl.TGoodsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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
@Tag(name = "t-goods-controller", description = "商品接口")
public class TGoodsController {
    @Autowired
    TGoodsServiceImpl tGoodsService;

    @PostMapping("/insert")
    @Operation(summary = "商品新增")
    public Map insert(@RequestBody @Valid TGoods entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return tGoodsService.insertGoods(entity);
    }

    @PostMapping("/delete")
    @Operation(summary = "商品删除")
    public String delete(@RequestBody String dataId) {
        return tGoodsService.deleteGoods((String) JSONObject.parseObject(dataId).get("dataId"));
    }

    @PostMapping("/list")
    @Operation(summary = "分页+条件查询商品列表，参数：\"pageNum\":0,\n" +
            "\"pageSize\":10,\n" + "\"keyWord\":\"要查的关键字，没有就为空查询全部\"")
    public Map list(@RequestBody Map params) {
        return tGoodsService.selectlist(params);
    }

    @PostMapping("/update")
    @Operation(summary = "更新操作")
    public String update(@RequestBody TGoods entity) {
        return tGoodsService.updateOne(entity);
    }

    @PostMapping("/detail")
    @Operation(summary = "根据dataid查询对应商品信息")
    public TGoods detail(@RequestBody String dataId) {
        return tGoodsService.detail((String)JSONObject.parseObject(dataId).get("dataId"));
    }
}
