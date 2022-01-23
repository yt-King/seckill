package com.yt.seckill.mapper;

import com.yt.seckill.entity.TGoods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2022-01-12
 */
public interface TGoodsMapper extends BaseMapper<TGoods> {
    //判断时间是否在秒杀时间内
    public int checktime(String dataId,String time);
    //判断时间是否在秒杀时间内
    public List<TGoods> selectByCondition(Map params);
    //count
    public int selectCount(Map params);
}
