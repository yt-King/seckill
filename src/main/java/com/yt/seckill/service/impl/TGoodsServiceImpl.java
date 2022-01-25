package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yt.seckill.entity.*;
import com.yt.seckill.mapper.TGoodsMapper;
import com.yt.seckill.mapper.TSeckillGoodsMapper;
import com.yt.seckill.service.ITGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.IdcardUtils;
import com.yt.seckill.utils.PageUtils;
import com.yt.seckill.utils.TimeUtils;
import com.yt.seckill.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.yt.seckill.utils.MD5Utils.string2MD5;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yt
 * @since 2022-01-12
 */
@Service
public class TGoodsServiceImpl extends ServiceImpl<TGoodsMapper, TGoods> implements ITGoodsService {
    @Autowired
    TGoodsMapper tGoodsMapper;
    @Autowired
    TSeckillGoodsMapper tSeckillGoodsMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TGoodsRuleServiceImpl tGoodsRuleService;
    @Autowired
    SysUserServiceImpl sysUserService;
    @Autowired
    TApplyRecordServiceImpl tApplyRecordService;

    /**
     * 功能描述:
     * 秒杀商品新增
     *
     * @param entity
     * @return java.util.Map
     * @author yt
     * @date 2022/1/13 11:29
     */
    @Transactional(rollbackFor = Exception.class)
    public Map insertGoods(TGoods entity) {
        Map map = new HashMap();
        TSeckillGoods tSeckillGoods = new TSeckillGoods();
        tGoodsMapper.insert(entity);
        //新增商品时更新秒杀商品的信息
        tSeckillGoods.setDataId(entity.getDataId());//商品新增完成后得到dataid进行赋值
        tSeckillGoods.setGoodsNum(entity.getGoodsNum());
        tSeckillGoodsMapper.insert(tSeckillGoods);
        map.put("msg", "新增成功");
        return map;
    }

    /**
     * 功能描述:
     * 商品删除
     *
     * @param dataId
     * @return java.lang.String
     * @author yt
     * @date 2022/1/23 15:42
     */
    @Transactional(rollbackFor = Exception.class)
    public String deleteGoods(String dataId) {
        //只更新一个属性，根据dataid设置isDelte
        UpdateWrapper<TGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("data_id",dataId).set("is_delete", "1");
        //is_delete置1，进行逻辑删除，判断返回值，=0说明该条记录不存在直接抛出异常
        if(tGoodsMapper.update(null, updateWrapper)<1)
            throw new RuntimeException("该商品不存在");
        Map map = new HashMap<>();
        map.put("data_id", dataId);
        tSeckillGoodsMapper.deleteByMap(map);
        return "删除成功";
    }

    /**
     * 功能描述:
     * 分页+条件查询列表
     *
     * @param params
     * @return java.util.Map
     * @author yt
     * @date 2022/1/23 22:16
     */
    public Map selectlist(Map params) {
        Map map = new HashMap();
        List<TGoods> list;
        int count = tGoodsMapper.selectCount(params);
        PageUtils.checkPage(params);
        list=tGoodsMapper.selectByCondition(params);
        map.put("count",count);
        map.put("list",list);
        return map;
    }

    /**
     * 功能描述:
     * 更新商品
     *
     * @param entity
     * @return java.lang.String
     * @author yt
     * @date 2022/1/23 22:49
     */
    @Transactional(rollbackFor = Exception.class)
    public String updateOne(TGoods entity) {
        UpdateWrapper<TGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("data_id",entity.getDataId());
        if(tGoodsMapper.update(entity,updateWrapper)<1)
            throw new RuntimeException("更新失败，商品不存在");
        //还需要对应的进行seckillgoods的更新，这里偷个懒省掉了。。。
        return "更新成功";
    }

    /**
     * 功能描述:
     * 单个商品查询，查询前先判断是否有参与活动资格并写入申请记录
     *
     * @param dataId
     * @return com.yt.seckill.entity.TGoods
     * @author yt
     * @date 2022/1/23 23:22
     */
    public TGoods detail(String dataId) {
        QueryWrapper<TGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("data_id",dataId);
        TGoods tGoods = tGoodsMapper.selectOne(queryWrapper);
        if(null==tGoods)
            throw new RuntimeException("商品不存在");
        //查询准入规则，从头里或请求里拿到token得到用户信息
        String token  = TokenUtil.getRequestToken(request);
        //根据token获取用户信息
        if(null==token)
            throw new RuntimeException("token为空，请重新登录");
        SysUser sysUser = (SysUser) redisTemplate.opsForValue().get(token);
        //查询准入规则
        TGoodsRule tGoodsRule = tGoodsRuleService.detail(dataId);
        //新增申请记录
        TApplyRecord tApplyRecord = new TApplyRecord();
        tApplyRecord.setUserId(sysUser.getUserId());
        tApplyRecord.setUserName(sysUser.getName());
        tApplyRecord.setGoodsId(tGoods.getDataId());
        tApplyRecord.setGoodsName(tGoods.getGoodsName());
        tApplyRecord.setApplyTime(new Date());
        //根据用户查询是否有参与活动的资格
        try {
            sysUserService.checkUserQualify(sysUser,tGoodsRule);
        }catch (Exception e){
            //结果不通过
            tApplyRecord.setApplyResult(1);
            tApplyRecordService.insertRecord(tApplyRecord);
            throw e;
        }
        //结果通过
        tApplyRecord.setApplyResult(0);
        tApplyRecordService.insertRecord(tApplyRecord);
        return tGoods;
    }

    public void checktime(String dataId) {
        String time = TimeUtils.getNowTime();
        if (1 > tGoodsMapper.checktime(dataId, time))
            throw new RuntimeException("不在秒杀时间内或该商品不存在");
    }
}
