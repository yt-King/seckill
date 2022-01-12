package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

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
     * 商品标题
     */
    private String goodsTitle;

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
    private Date goodsStart;

    /**
     * 秒杀结束时间
     */
    private Date goodsEnd;

    /**
     * 秒杀商品数量
     */
    private Integer goodsNum;

    /**
     * 商品原价
     */
    private String goodsPrice;

    /**
     * 商品秒杀价
     */
    private String goodsKillPrice;

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
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
