package com.yt.seckill.controller;


import com.google.common.util.concurrent.RateLimiter;
import com.yt.seckill.entity.Dto.ParamTempDto;
import com.yt.seckill.entity.TOrderRecord;
import com.yt.seckill.service.impl.SysUserServiceImpl;
import com.yt.seckill.service.impl.TOrderRecordServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    SysUserServiceImpl sysUserService;

    //每秒放行100个请求
    RateLimiter rateLimiter = RateLimiter.create(100);

    @PostMapping("/createtemp")
    @Operation(summary = "用户下单生成临时待支付订单")
    public TOrderRecord createTempOrder(@RequestBody ParamTempDto params, HttpServletRequest request) throws Exception {
        //非阻塞式获取令牌：请求进来后，若令牌桶里没有足够的令牌，会尝试等待设置好的时间（这里写了1000ms），其会自动判断在1000ms后，
        //这个请求能不能拿到令牌，如果不能拿到，直接返回抢购失败。如果timeout设置为0，则等于阻塞时获取令牌。
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS))
            throw new RuntimeException("下单失败，限流");
        TOrderRecord tOrderRecord = tOrderRecordService.createTempOrder(params);
        return tOrderRecord;
    }

    @PostMapping("/createreal")
    @Operation(summary = "用户支付成功后下单")//点击确认支付按钮的时候调用接口,支付前校验是否已经下单（校验订单号）
    public String createRealOrder(@RequestBody TOrderRecord tOrderRecord) throws Exception {
        //校验订单号是否已经下单（下单后十分钟内有效）
        tOrderRecordService.doverfy(tOrderRecord.getOrderPerson(),tOrderRecord.getDataId(),tOrderRecord.getOrderId());
        //非阻塞式获取令牌：请求进来后，若令牌桶里没有足够的令牌，会尝试等待设置好的时间（这里写了1000ms），其会自动判断在1000ms后，
        //这个请求能不能拿到令牌，如果不能拿到，直接返回抢购失败。如果timeout设置为0，则等于阻塞时获取令牌。
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS))
            throw new RuntimeException("抢购失败，限流");
        return tOrderRecordService.createOrder(tOrderRecord);
    }
}
