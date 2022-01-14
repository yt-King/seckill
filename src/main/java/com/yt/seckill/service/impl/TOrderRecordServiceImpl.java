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
     * 创建临时订单，用于给用户支付，不写入数据库库，没有订单号，支付成功后写入数据库时生成订单号
     *
     * @param dataid
     * @return com.yt.seckill.entity.TOrderRecord
     * @author yt
     * @date 2022/1/14 22:49
     */
    public TOrderRecord createTempOrder(String dataid) throws Exception {
        //校验库存
        TSeckillGoods goods = tSeckillGoodsService.checkGoods(dataid);
        //创建临时订单，发给用户用以支付，不写入数据库
        TOrderRecord order = new TOrderRecord();
        order.setDataId(goods.getDataId());
        order.setOrderTime(new Date());
//        order.setOrderPerson("yt");
        return order;
    }

    /**
     * 功能描述:
     * 用户支付时调用方法将订单写入数据库表示抢购成功
     *
     * @param tOrderRecord
     * @return java.lang.String
     * @author yt
     * @date 2022/1/14 22:48
     */
    public String createOrder(TOrderRecord tOrderRecord) throws Exception {
        //校验库存
        TSeckillGoods goods = tSeckillGoodsService.checkGoods(tOrderRecord.getDataId());
        //乐观锁更新库存呢
        tSeckillGoodsService.saleOptimistic(goods);
        //创建订单
        tOrderRecordMapper.insert(tOrderRecord);
        return "抢购成功";
    }
}
