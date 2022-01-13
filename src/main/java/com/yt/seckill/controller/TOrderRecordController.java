package com.yt.seckill.controller;


import com.yt.seckill.entity.TOrderRecord;
import com.yt.seckill.service.impl.TOrderRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TOrderRecordController {
    @Autowired
    TOrderRecordServiceImpl tOrderRecordService;
    @RequestMapping("/create")
    public TOrderRecord createWrongOrder(Integer dataid) throws Exception {
        TOrderRecord tOrderRecord = tOrderRecordService.createOrder(dataid);
        return tOrderRecord;
    }
}
