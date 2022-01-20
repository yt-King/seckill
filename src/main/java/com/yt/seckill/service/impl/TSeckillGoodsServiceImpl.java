package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yt.seckill.entity.Dto.ParamTempDto;
import com.yt.seckill.entity.Enum.CacheKey;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TSeckillGoods;
import com.yt.seckill.mapper.TSeckillGoodsMapper;
import com.yt.seckill.service.ITSeckillGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class TSeckillGoodsServiceImpl extends ServiceImpl<TSeckillGoodsMapper, TSeckillGoods> implements ITSeckillGoodsService {
    @Autowired
    TSeckillGoodsMapper tSeckillGoodsMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TGoodsServiceImpl tGoodsService;

    /**
     * 功能描述:
     * 检查是否还有库存
     *
     * @param dataid
     * @return com.yt.seckill.entity.TSeckillGoods
     * @author yt
     * @date 2022/1/13 21:30
     */
    public TSeckillGoods checkGoods(String dataid) {
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

    /**
     * 功能描述:
     * 获取验证值以掩藏抢购的接口
     *
     * @param paramTempDto
     * @return java.lang.String
     * @author yt
     * @date 2022/1/15 16:13
     */
    public String getVerifyHash(ParamTempDto paramTempDto) throws Exception {
        // 验证是否在抢购时间内
        tGoodsService.checktime(paramTempDto.getGoodsId());

//        // 检查用户合法性
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        //查询cookie,使用封装好的工具类
//        String userTicket = CookieUtils.getCookieValue(request, "userTicket");
//        if (StringUtils.isEmpty(userTicket)) {
//            throw new RuntimeException("您还未登录或登录状态已失效");
//        }
//        SysUser user = (SysUser) redisTemplate.opsForValue().get("user:" + userTicket);
//        if (null == user) {
//            throw new RuntimeException("您还未登录或登录状态已失效");
//        }

        // 盐+用户id+商品id生成hash进行第一次MD5加密，用于临时订单接口生成真正的校验码
        String verify = CacheKey.SALT_KEY.getKey() + paramTempDto.getUserId() + paramTempDto.getGoodsId();
        String verifyHash = DigestUtils.md5DigestAsHex(verify.getBytes());

        //第二次加密，用于真正下单支付时校验
        String verify2 = CacheKey.SEC_SALT_KEY.getKey() + verifyHash;
        String verifyHash2 = DigestUtils.md5DigestAsHex(verify2.getBytes());

        // 将第二次加密后的存入redis
        String hashKey = CacheKey.VERIFY_KEY.getKey() + "_" + paramTempDto.getUserId() + "_" + paramTempDto.getGoodsId();
        redisTemplate.opsForValue().set(hashKey, verifyHash2, 600, TimeUnit.SECONDS);

        //返回第一次加密的verifyHash
        return verifyHash;
    }

}
