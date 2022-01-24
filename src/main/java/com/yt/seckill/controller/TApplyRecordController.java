package com.yt.seckill.controller;


import com.alibaba.fastjson.JSONObject;
import com.yt.seckill.entity.TApplyRecord;
import com.yt.seckill.entity.TOverdueRecord;
import com.yt.seckill.service.impl.TApplyRecordServiceImpl;
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
@RequestMapping("/applyRecord")
@Tag(name = "t-apply-record-controller", description = "申请记录接口")
public class TApplyRecordController {
    @Autowired
    TApplyRecordServiceImpl tApplyRecordService;

    @PostMapping("/add")
    @Operation(summary = "申请记录新增")
    public String insert(@RequestBody TApplyRecord entity, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return tApplyRecordService.insertRecord(entity);
    }

    @PostMapping("/list")
    @Operation(summary = "分页+条件查询申请记录，参数：根据id查询")
    public Map list(@RequestBody Map params) {
        return tApplyRecordService.selectlist(params);
    }

    @PostMapping("/update")
    @Operation(summary = "更新操作，根据id更新")
    public String update(@RequestBody TApplyRecord entity) {
        return tApplyRecordService.updateOne(entity);
    }

    @PostMapping("/detail")
    @Operation(summary = "根据id查询对应用户信息")
    public TApplyRecord detail(@RequestBody String Id) {
        return tApplyRecordService.detail((String) JSONObject.parseObject(Id).get("Id"));
    }

    @PostMapping("/delete")
    @Operation(summary = "逾期记录逻辑删除")
    public String delete(@RequestBody String Id) {
        return tApplyRecordService.deleteUser((String) JSONObject.parseObject(Id).get("Id"));
    }
}
