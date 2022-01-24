package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TGoods;
import com.yt.seckill.entity.TOverdueRecord;
import com.yt.seckill.mapper.TOverdueRecordMapper;
import com.yt.seckill.service.ITOverdueRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.IdcardUtils;
import com.yt.seckill.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yt.seckill.utils.MD5Utils.string2MD5;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
@Service
public class TOverdueRecordServiceImpl extends ServiceImpl<TOverdueRecordMapper, TOverdueRecord> implements ITOverdueRecordService {
    @Autowired
    TOverdueRecordMapper tOverdueRecordMapper;

    public String insertRecord(TOverdueRecord entity) {
        tOverdueRecordMapper.insert(entity);
        return "新增成功";
    }

    public Map selectlist(Map params) {
        Map map = new HashMap();
        List<TOverdueRecord> list;
        int count = tOverdueRecordMapper.selectCount(params);
        PageUtils.checkPage(params);
        list = tOverdueRecordMapper.selectByCondition(params);
        map.put("count", count);
        map.put("list", list);
        return map;
    }

    public String updateOne(TOverdueRecord entity) {
        UpdateWrapper<TOverdueRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("data_id", entity.getDataId());
        if (tOverdueRecordMapper.update(entity, updateWrapper) < 1)
            throw new RuntimeException("更新失败，记录不存在");
        return "更新成功";
    }

    public TOverdueRecord detail(String dataId) {
        QueryWrapper<TOverdueRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("data_id", dataId);
        TOverdueRecord tOverdueRecord = tOverdueRecordMapper.selectOne(queryWrapper);
        if (null == tOverdueRecord)
            throw new RuntimeException("记录不存在");
        return tOverdueRecord;
    }

    public String deleteUser(String dataId) {
        //只更新一个属性，根据dataid设置isDelte
        UpdateWrapper<TOverdueRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("data_id",dataId).set("is_delete", "1");
        //is_delete置1，进行逻辑删除，判断返回值，=0说明该条记录不存在直接抛出异常
        if(tOverdueRecordMapper.update(null, updateWrapper)<1)
            throw new RuntimeException("该记录不存在");
        return "删除成功";
    }
}
