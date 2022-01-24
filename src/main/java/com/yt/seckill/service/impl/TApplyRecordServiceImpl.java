package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yt.seckill.entity.TApplyRecord;
import com.yt.seckill.entity.TApplyRecord;
import com.yt.seckill.mapper.TApplyRecordMapper;
import com.yt.seckill.service.ITApplyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
@Service
public class TApplyRecordServiceImpl extends ServiceImpl<TApplyRecordMapper, TApplyRecord> implements ITApplyRecordService {
    @Autowired
    TApplyRecordMapper tApplyRecordMapper;
    
    public String insertRecord(TApplyRecord entity) {
        tApplyRecordMapper.insert(entity);
        return "新增成功";
    }

    public Map selectlist(Map params) {
        Map map = new HashMap();
        List<TApplyRecord> list;
        int count = tApplyRecordMapper.selectCount(params);
        PageUtils.checkPage(params);
        list = tApplyRecordMapper.selectByCondition(params);
        map.put("count", count);
        map.put("list", list);
        return map;
    }

    public String updateOne(TApplyRecord entity) {
        UpdateWrapper<TApplyRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", entity.getId());
        if (tApplyRecordMapper.update(entity, updateWrapper) < 1)
            throw new RuntimeException("更新失败，记录不存在");
        return "更新成功";
    }

    public TApplyRecord detail(String Id) {
        QueryWrapper<TApplyRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Id", Id);
        TApplyRecord tApplyRecord = tApplyRecordMapper.selectOne(queryWrapper);
        if (null == tApplyRecord)
            throw new RuntimeException("记录不存在");
        return tApplyRecord;
    }

    public String deleteUser(String Id) {
        //只更新一个属性，根据dataid设置isDelte
        UpdateWrapper<TApplyRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("Id",Id).set("is_delete", "1");
        //is_delete置1，进行逻辑删除，判断返回值，=0说明该条记录不存在直接抛出异常
        if(tApplyRecordMapper.update(null, updateWrapper)<1)
            throw new RuntimeException("该记录不存在");
        return "删除成功";
    }
}
