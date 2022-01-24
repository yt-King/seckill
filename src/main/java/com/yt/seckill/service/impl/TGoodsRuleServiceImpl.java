package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TGoods;
import com.yt.seckill.entity.TGoodsRule;
import com.yt.seckill.entity.TOverdueRecord;
import com.yt.seckill.mapper.TGoodsRuleMapper;
import com.yt.seckill.service.ITGoodsRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
@Service
public class TGoodsRuleServiceImpl extends ServiceImpl<TGoodsRuleMapper, TGoodsRule> implements ITGoodsRuleService {
    @Autowired
    TGoodsRuleMapper tGoodsRuleMapper;
    @Autowired
    TGoodsServiceImpl tGoodsService;

    /**
     * 功能描述:
     * 给指定商品id添加准入规则
     *
     * @param entity
     * @return java.lang.String
     * @author yt
     * @date 2022/1/24 21:26
     */
    public String insertRecord(TGoodsRule entity) {
        //先检查商品id是否存在
        TGoods tGoods = tGoodsService.detail(entity.getDataId());
        //将准入规则写入
        tGoodsRuleMapper.insert(entity);
        return "添加成功";
    }

    public TGoodsRule detail(String dataId) {
        QueryWrapper<TGoodsRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("data_id",dataId);
        TGoodsRule tGoodsRule = tGoodsRuleMapper.selectOne(queryWrapper);
        if(null==tGoodsRule)
            throw new RuntimeException("记录不存在");
        return tGoodsRule;
    }

    public String deleteRule(String dataId) {
        //只更新一个属性，根据dataid设置isDelte
        UpdateWrapper<TGoodsRule> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("data_id",dataId).set("is_delete", "1");
        //is_delete置1，进行逻辑删除，判断返回值，=0说明该条记录不存在直接抛出异常
        if(tGoodsRuleMapper.update(null, updateWrapper)<1)
            throw new RuntimeException("该记录不存在");
        return "删除成功";
    }
}
