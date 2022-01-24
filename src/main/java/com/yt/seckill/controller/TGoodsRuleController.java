package com.yt.seckill.controller;


import com.alibaba.fastjson.JSONObject;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TApplyRecord;
import com.yt.seckill.entity.TGoodsRule;
import com.yt.seckill.service.impl.TGoodsRuleServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
@RestController
@RequestMapping("/goodsRule")
@Tag(name = "t-goods-rule-controller", description = "商品风险引擎配置接口")
public class TGoodsRuleController {
    @Autowired
    TGoodsRuleServiceImpl tGoodsRuleService;

    @PostMapping("/setRule")
    @Operation(summary = "给指定商品添加风险管控配置")
    public String setRule(@RequestBody @Valid TGoodsRule entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return tGoodsRuleService.insertRecord(entity);
    }

    @PostMapping("/detail")
    @Operation(summary = "根据dataID查询对应信息")
    public TGoodsRule detail(@RequestBody String dataId) {
        return tGoodsRuleService.detail((String) JSONObject.parseObject(dataId).get("dataId"));
    }

    @PostMapping("/delete")
    @Operation(summary = "规则删除")
    public String delete(@RequestBody String dataId) {
        return tGoodsRuleService.deleteRule((String) JSONObject.parseObject(dataId).get("dataId"));
    }
}
