package com.yt.seckill.mapper;

import com.yt.seckill.entity.TSeckillGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
public interface TSeckillGoodsMapper extends BaseMapper<TSeckillGoods> {
    //乐观锁更新库存
    int updateByOptimistic(TSeckillGoods goods);
}
