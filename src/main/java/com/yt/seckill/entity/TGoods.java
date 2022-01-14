package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 商品实体类
 * </p>
 *
 * @author yt
 * @since 2022-01-12
 */
@Data
@ApiModel(description= "商品实体类")
@EqualsAndHashCode(callSuper = false)
public class TGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @Schema(hidden = true)
    @TableId(type = IdType.ASSIGN_UUID)
    private String dataId;

    /**
     * 商品名称
     */
    @Schema(name = "goodsName", description = "商品名称", example = "iPhone")
    private String goodsName;

    /**
     * 商品标题
     */
    @Schema(name = "goodsTitle", description = "商品标题", example = "iPhone13ProMax远峰蓝1Tb")
    private String goodsTitle;

    /**
     * 商品图片
     */
    @Schema(name = "goodsImg", description = "商品图片")
    private String goodsImg;

    /**
     * 商品详情
     */
    @Schema(name = "goodsDetail", description = "商品详情")
    private String goodsDetail;

    /**
     * 秒杀开始时间
     */
    @Schema(name = "goodsStart", description = "秒杀开始时间", example = "2022-01-11 15:0:0")
    private Date goodsStart;

    /**
     * 秒杀结束时间
     */
    @Schema(name = "goodsEnd", description = "秒杀结束时间", example = "2022-01-11 15:10:0")
    private Date goodsEnd;

    /**
     * 秒杀商品数量
     */
    @Schema(name = "goodsNum", description = "秒杀商品数量", example = "1000")
    private Integer goodsNum;

    /**
     * 商品原价
     */
    @Schema(name = "goodsPrice", description = "商品原价", example = "14999")
    private String goodsPrice;

    /**
     * 商品秒杀价
     */
    @Schema(name = "goodsKillPrice", description = "商品秒杀价", example = "9999")
    private String goodsKillPrice;

    /**
     * 预留字段1
     */
    @Schema(hidden = true)
    private String goodsOne;

    /**
     * 预留字段2
     */
    @Schema(hidden = true)
    private String goodsSec;

    /**
     * 逻辑删除
     */
    @Schema(hidden = true)
    private Integer isDelete;

    /**
     * 创建时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
