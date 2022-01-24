package com.yt.seckill.mapper;

import com.yt.seckill.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yt.seckill.entity.TGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2021-11-20
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    //获取用户权限列表
    List<Map<String, Object>> getUserPower(String userName);
    //数据插入
    int insert(SysUser entity);
    //根据电话号码查询数量
    int selectByTel(String tel);
    //根据电话号码查询实体信息
    SysUser selectAllByTel(String tel);
    //用户列表查询
    List<SysUser> selectByCondition(Map params);
    //count
    int selectCount(Map params);
}
