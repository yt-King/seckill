package com.yt.seckill.service.impl;

import com.yt.seckill.entity.Dto.ParamTempDto;
import com.yt.seckill.entity.Enum.CacheKey;
import com.yt.seckill.entity.TOrderRecord;
import com.yt.seckill.entity.TSeckillGoods;
import com.yt.seckill.mapper.TOrderRecordMapper;
import com.yt.seckill.service.ITOrderRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 功能描述:
     * 创建临时订单，用于给用户支付，不写入数据库库，没有订单号，支付成功后写入数据库时生成订单号
     *
     * @param params
     * @return com.yt.seckill.entity.TOrderRecord
     * @author yt
     * @date 2022/1/14 22:49
     */
    public TOrderRecord createTempOrder(ParamTempDto params) throws Exception {
        //校验库存
        TSeckillGoods goods = tSeckillGoodsService.checkGoods(params.getGoodsId());
        //创建临时订单，发给用户用以支付，不写入数据库
        TOrderRecord order = new TOrderRecord();
        order.setDataId(goods.getDataId());
        order.setOrderTime(new Date());
        order.setOrderPerson(params.getUserId());
        //将临时VerifyHash再进行MD5加密生成真正的校验码作为订单号填入用于支付时校验
        String verify = CacheKey.SEC_SALT_KEY.getKey() + params.getVerifyHash();
        String verifyHash = DigestUtils.md5DigestAsHex(verify.getBytes());
        order.setOrderId(verifyHash);
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

    /**
     * 功能描述:
     * 验证hash值合法性
     *
     * @param userId
     * @param goodsId
     * @param verifyHash
     * @return void
     * @author yt
     * @date 2022/1/17 18:41
     */
    public void doverfy(String userId,String goodsId,String verifyHash) throws Exception {
        String hashKey = CacheKey.VERIFY_KEY.getKey() + "_" + userId + "_" + goodsId;
        String verifyHashInRedis = (String) redisTemplate.opsForValue().get(hashKey);
        if (!verifyHash.equals(verifyHashInRedis))
            throw new RuntimeException("hash值与Redis中不符合");
    }
}
