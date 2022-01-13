package com.yt.seckill.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TSeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    private String dataId;

    /**
     * 秒杀商品数量
     */
    private Integer goodsNum;

    /**
     * 已售数量
     */
    private Integer goodsSale;

    /**
     * 版本号
     */
    private String goodsVersion;


}
