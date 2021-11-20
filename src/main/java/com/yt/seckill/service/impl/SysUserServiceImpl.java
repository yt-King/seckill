package com.yt.seckill.service.impl;

import com.yt.seckill.entity.SysUser;
import com.yt.seckill.mapper.SysUserMapper;
import com.yt.seckill.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yt.seckill.utils.IdcardUtils;
import com.yt.seckill.utils.validatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Map insertUser(SysUser entity){
        Map map = new HashMap();
        //识别省份证号，填入基本信息
        String idcard = entity.getIdCard();
        switch (idcard.length()){
            case 18:
                if(!IdcardUtils.validateIdCard18(idcard)) {
                    map.put("msg", "身份证号错误");
                    return map;
                }
                break;
            case 15:
                if(!IdcardUtils.validateIdCard15(idcard)) {
                    map.put("msg", "身份证号错误");
                    return map;
                }
                break;
            default:
                if(IdcardUtils.validateIdCard10(idcard)==null) {
                    map.put("msg", "身份证号错误");
                    return map;
                }
                else {
                    if(IdcardUtils.validateIdCard10(idcard)[2].equals("false")){
                        map.put("msg", "身份证号错误");
                        return map;
                    }
                }
        }
        entity.setAge(IdcardUtils.getAgeByIdCard(idcard));
        entity.setBirth(IdcardUtils.getBirthByIdCard(idcard));
        entity.setSex(IdcardUtils.getGenderByIdCard(idcard));
        //校验手机号
        if(!validatorUtil.isMobile(entity.getTel())){
            map.put("msg", "手机号错误");
            return map;
        }
        //MD5密码加密
        entity.setPassWord(string2MD5(entity.getPassWord()));
        sysUserMapper.insert(entity);
        map.put("msg", "注册成功");
        return map;
    }
}
