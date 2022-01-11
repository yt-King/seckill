package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yt
 * @since 2022-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String dataId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsImg;

    /**
     * 商品详情
     */
    private String goodsDetail;

    /**
     * 秒杀开始时间
     */
    private LocalDateTime goodsStart;

    /**
     * 秒杀结束时间
     */
    private LocalDateTime goodsEnd;

    /**
     * 秒杀商品数量
     */
    private Integer goodsNum;

    /**
     * 预留字段1
     */
    private String goodsOne;

    /**
     * 预留字段2
     */
    private String goodsSec;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


}
