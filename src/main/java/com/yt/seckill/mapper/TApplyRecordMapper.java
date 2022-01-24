package com.yt.seckill.mapper;

import com.yt.seckill.entity.TApplyRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yt.seckill.entity.TOverdueRecord;

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
public interface TApplyRecordMapper extends BaseMapper<TApplyRecord> {
    //逾期记录列表查询
    List<TApplyRecord> selectByCondition(Map params);
    //count
    int selectCount(Map params);
}
