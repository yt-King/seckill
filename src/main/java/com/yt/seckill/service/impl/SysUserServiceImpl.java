package com.yt.seckill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yt.seckill.entity.ParamUserDto;
import com.yt.seckill.entity.SysUser;
import com.yt.seckill.mapper.SysUserMapper;
import com.yt.seckill.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.CookieUtils;
import com.yt.seckill.utils.IdcardUtils;
import com.yt.seckill.utils.MD5Utils;
import com.yt.seckill.utils.validatorUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
        if (sysUserMapper.selectByTel(entity.getTel()) > 0) {
            map.put("msg", "该手机号以注册");
            return map;
        }
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
        if (sysUserMapper.selectByTel(entity.getTel()) != 1) {
            map.put("msg", "账号或密码错误");
            return map;
        }
        SysUser sysUser = sysUserMapper.selectAllByTel(entity.getTel());
        if (!MD5Utils.passwordIsTrue(entity.getPassWord(), sysUser.getPassWord())) {
            map.put("msg", "账号或密码错误");
            return map;
        }
        //生成cookie
        String ticket = CookieUtils.UUID();
        redisTemplate.opsForValue().set("user:" + ticket, sysUser);
        CookieUtils.setCookie(request, response, "userTicket", ticket);
        map.put("msg", "登录成功");
        return map;
    }

    /**
     * 通过cookie获取用户信息
     * @param userTicket
     * @param request
     * @param response
     * @return
     */
    public SysUser getUserByCookie(String userTicket,HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        SysUser user = (SysUser) redisTemplate.opsForValue().get("user:" + userTicket);
        if (null != user) {
            CookieUtils.setCookie(request, response, "userTicket", userTicket);
        }
        return user;
    }
}
