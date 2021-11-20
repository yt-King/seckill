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
    int insert(SysUser entity);
}
