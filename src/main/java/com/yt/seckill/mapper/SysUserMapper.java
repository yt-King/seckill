package com.yt.seckill.mapper;

import com.yt.seckill.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
    //数据插入
    int insert(SysUser entity);
    //根据电话号码查询数量
    int selectByTel(String tel);
    //根据电话号码查询实体信息
    SysUser selectAllByTel(String tel);
}
