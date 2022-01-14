package com.yt.seckill.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 秒杀商品实体类
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
@Data
@ApiModel(description= "秒杀商品实体类")
@EqualsAndHashCode(callSuper = false)
public class TSeckillGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId
    @Schema(name = "dataId", description = "uuid，新增商品时一起写入")
    private String dataId;

    /**
     * 秒杀商品数量
     */
    @Schema(name = "goodsNum", description = "秒杀商品数量，新增商品时一起写入")
    private Integer goodsNum;

    /**
     * 已售数量
     */
    @Schema(name = "goodsSale", description = "已售数量")
    private Integer goodsSale;

    /**
     * 版本号
     */
    @Schema(name = "goodsVersion", description = "版本号，用于乐观锁")
    private String goodsVersion;


}
