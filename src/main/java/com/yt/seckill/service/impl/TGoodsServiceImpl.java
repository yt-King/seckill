package com.yt.seckill.service.impl;

import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TGoods;
import com.yt.seckill.mapper.TGoodsMapper;
import com.yt.seckill.service.ITGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.IdcardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.yt.seckill.utils.MD5Utils.string2MD5;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-01-12
 */
@Service
public class TGoodsServiceImpl extends ServiceImpl<TGoodsMapper, TGoods> implements ITGoodsService {
    @Autowired
    TGoodsMapper tGoodsMapper;

    /**
     * 新增秒杀商品
     *
     * @param entity
     * @return
     */
    public Map insertGoods(TGoods entity) {
        Map map = new HashMap();
        tGoodsMapper.insert(entity);
        map.put("msg", "新增成功");
        return map;
    }
}
