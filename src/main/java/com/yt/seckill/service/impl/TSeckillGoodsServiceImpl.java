package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yt.seckill.entity.TSeckillGoods;
import com.yt.seckill.mapper.TSeckillGoodsMapper;
import com.yt.seckill.service.ITSeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
@Service
public class TSeckillGoodsServiceImpl extends ServiceImpl<TSeckillGoodsMapper, TSeckillGoods> implements ITSeckillGoodsService {
    @Autowired
    TSeckillGoodsMapper tSeckillGoodsMapper;
    /**
     * 功能描述:
     * 检查是否还有库存
     *
     * @param dataid
     * @return com.yt.seckill.entity.TSeckillGoods
     * @author yt
     * @date 2022/1/13 21:30
     */
    public TSeckillGoods checkGoods(int dataid) {
        TSeckillGoods tSeckillGoods = tSeckillGoodsMapper.selectOne(new QueryWrapper<TSeckillGoods>().eq("data_id", dataid));
        if (tSeckillGoods.getGoodsNum().equals(tSeckillGoods.getGoodsSale())) {
            throw new RuntimeException("库存不足");
        }
        return tSeckillGoods;
    }

    /**
     * 功能描述:
     * 乐观锁更新库存
     *
     * @param goods
     * @return void
     * @author yt
     * @date 2022/1/13 21:30
     */
    public void saleOptimistic(TSeckillGoods goods) {
        int count = tSeckillGoodsMapper.updateByOptimistic(goods);
        if (count == 0) {
            throw new RuntimeException("并发更新库存失败，version不匹配");
        }
    }
}
