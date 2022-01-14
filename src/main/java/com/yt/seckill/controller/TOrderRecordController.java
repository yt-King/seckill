package com.yt.seckill.controller;


import com.yt.seckill.entity.TOrderRecord;
import com.yt.seckill.service.impl.TOrderRecordServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
@RestController
@RequestMapping("/order")
@Tag(name = "t-order-record-controller", description = "订单接口")
public class TOrderRecordController {
    @Autowired
    TOrderRecordServiceImpl tOrderRecordService;
    @PostMapping("/create")
    @Operation(summary = "用户下单")
    public TOrderRecord createWrongOrder(String dataid) throws Exception {
        TOrderRecord tOrderRecord = tOrderRecordService.createOrder(dataid);
        return tOrderRecord;
    }
}
