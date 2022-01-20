package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yt.seckill.entity.Dto.ParamUserDto;
import com.yt.seckill.entity.Enum.CacheKey;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.mapper.SysUserMapper;
import com.yt.seckill.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.yt.seckill.utils.MD5Utils.string2MD5;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-11-20
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Resource(name = "IpCountRedisTemplate")
    RedisTemplate ipCountRedisTemplate;//专门用于统计IP访问次数的redis
    @Value("${seckill.ALLOW_COUNT}")
    int ALLOW_COUNT; //接口允许访问最大次数

    public List<Map<String, Object>> getUserPower(String userId) {
       return sysUserMapper.getUserPower(userId);
    }

    /**
     * 注册
     *
     * @param entity
     * @return
     */
    public Map insertUser(SysUser entity) {
        Map map = new HashMap();
        //通过在实体类上添加自定义注解校验身份证号，校验完后填入基本信息
        String idcard = entity.getIdCard();
        entity.setAge(IdcardUtils.getAgeByIdCard(idcard));
        entity.setBirth(IdcardUtils.getBirthByIdCard(idcard));
        entity.setSex(IdcardUtils.getGenderByIdCard(idcard));
        entity.setAddress(IdcardUtils.getProvinceByIdCard(idcard));
        //通过在实体类上添加自定义注解校校验手机号，然后判断手机号是否被注册
        if (sysUserMapper.selectByTel(entity.getTel()) > 0)
            throw new RuntimeException("该手机号以注册");
        //MD5密码加密
        entity.setPassWord(string2MD5(entity.getPassWord()));
        sysUserMapper.insert(entity);
        map.put("msg", "注册成功");
        return map;
    }

    /**
     * 登录
     *
     * @param entity
     * @param request
     * @param response
     * @return
     */
    public Map login(ParamUserDto entity, HttpServletRequest request, HttpServletResponse response) {
        Map map = new HashMap();
        if (sysUserMapper.selectByTel(entity.getTel()) != 1)
            throw new RuntimeException("账号或密码错误");
        SysUser sysUser = sysUserMapper.selectAllByTel(entity.getTel());
        if (!MD5Utils.passwordIsTrue(entity.getPassWord(), sysUser.getPassWord()))
            throw new RuntimeException("账号或密码错误");
        //生成token——日期加用户id再进行MD5加密
        String token = string2MD5(TimeUtils.getCuitPreTime()+sysUser.getUserId());
        redisTemplate.opsForValue().set(token, sysUser, 60 * 60 * 12, TimeUnit.SECONDS);
        map.put("token", token);
        map.put("msg", "登录成功");
        return map;
    }

    /**
     * 通过cookie获取用户信息
     *
     * @param userTicket
     * @param request
     * @param response
     * @return
     */
//    public SysUser getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
//        if (StringUtils.isEmpty(userTicket)) {
//            return null;
//        }
//        SysUser user = (SysUser) redisTemplate.opsForValue().get("user:" + userTicket);
//        if (null != user) {
//            CookieUtils.setCookie(request, response, "userTicket", userTicket);
//        }
//        return user;
//    }

    /**
     * 功能描述:
     * ip访问接口次数加1
     *
     * @param request
     * @return int
     * @author yt
     * @date 2022/1/17 18:40
     */
    public int addIpCount(HttpServletRequest request) throws Exception {
        String ipAddress = IpUtil.getIpAddr(request);
        String limitKey = CacheKey.LIMIT_KEY.getKey() + "_" + ipAddress;
        String limitNum = (String) ipCountRedisTemplate.opsForValue().get(limitKey);
        int limit = -1;
        if (limitNum == null) {
            ipCountRedisTemplate.opsForValue().set(limitKey, "0", 600, TimeUnit.SECONDS);
        } else {
            limit = Integer.parseInt(limitNum) + 1;
            ipCountRedisTemplate.opsForValue().set(limitKey, String.valueOf(limit), 600, TimeUnit.SECONDS);
        }
        return limit;
    }

    /**
     * 功能描述:
     * 检查IP访问接口次数
     *
     * @param request
     * @return void
     * @author yt
     * @date 2022/1/17 18:40
     */
    public void CheckIpIsBanned(HttpServletRequest request) {
        String ipAddress = IpUtil.getIpAddr(request);
        String limitKey = CacheKey.LIMIT_KEY.getKey() + "_" + ipAddress;
        String limitNum = (String) ipCountRedisTemplate.opsForValue().get(limitKey);
        if (null == limitNum) return;
        if (Integer.parseInt(limitNum) >= ALLOW_COUNT)
            throw new RuntimeException("禁止频繁访问，请十分钟后再试");
    }
}
