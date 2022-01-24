package com.yt.seckill.controller;


import com.alibaba.fastjson.JSONObject;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TOverdueRecord;
import com.yt.seckill.service.impl.TOrderRecordServiceImpl;
import com.yt.seckill.service.impl.TOverdueRecordServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
@RestController
@RequestMapping("/overdueRecord")
@Tag(name = "t-overdue-record-controller", description = "逾期记录接口")
public class TOverdueRecordController {
    @Autowired
    TOverdueRecordServiceImpl tOverdueRecordService;

    @PostMapping("/add")
    @Operation(summary = "逾期用户记录新增")
    public String insert(@RequestBody @Valid TOverdueRecord entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return tOverdueRecordService.insertRecord(entity);
    }

    @PostMapping("/list")
    @Operation(summary = "分页+条件查询逾期记录，参数：根据用户名查询")
    public Map list(@RequestBody Map params) {
        return tOverdueRecordService.selectlist(params);
    }

    @PostMapping("/update")
    @Operation(summary = "更新操作，根据data_id更新")
    public String update(@RequestBody TOverdueRecord entity) {
        return tOverdueRecordService.updateOne(entity);
    }

    @PostMapping("/detail")
    @Operation(summary = "根据data_id查询对应用户信息")
    public TOverdueRecord detail(@RequestBody String dataId) {
        return tOverdueRecordService.detail((String) JSONObject.parseObject(dataId).get("dataId"));
    }

    @PostMapping("/delete")
    @Operation(summary = "逾期记录逻辑删除")
    public String delete(@RequestBody String dataId) {
        return tOverdueRecordService.deleteUser((String) JSONObject.parseObject(dataId).get("dataId"));
    }
}
