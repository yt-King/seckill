package com.yt.seckill.service.impl;

import com.yt.seckill.entity.TOrderRecord;
import com.yt.seckill.entity.TSeckillGoods;
import com.yt.seckill.mapper.TOrderRecordMapper;
import com.yt.seckill.service.ITOrderRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
@Service
public class TOrderRecordServiceImpl extends ServiceImpl<TOrderRecordMapper, TOrderRecord> implements ITOrderRecordService {
    @Autowired
    TOrderRecordMapper tOrderRecordMapper;
    @Autowired
    TSeckillGoodsServiceImpl tSeckillGoodsService;

    /**
     * 功能描述:
     * 订单生成
     *
     * @param dataid
     * @return com.yt.seckill.entity.TOrderRecord
     * @author yt
     * @date 2022/1/13 21:30
     */
    public TOrderRecord createOrder(String dataid) throws Exception {
        //校验库存
        TSeckillGoods goods = tSeckillGoodsService.checkGoods(dataid);
        //乐观锁更新库存呢
        tSeckillGoodsService.saleOptimistic(goods);
        //创建订单
        TOrderRecord order = new TOrderRecord();
        order.setDataId(goods.getDataId());
        order.setOrderTime(new Date());
//        order.setOrderPerson("yt");
        tOrderRecordMapper.insert(order);
        return order;
    }
}
