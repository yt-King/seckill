package com.yt.seckill.mapper;

import com.yt.seckill.entity.SysUser;
import com.yt.seckill.entity.TOverdueRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
public interface TOverdueRecordMapper extends BaseMapper<TOverdueRecord> {
    //逾期记录列表查询
    List<TOverdueRecord> selectByCondition(Map params);
    //count
    int selectCount(Map params);
}
