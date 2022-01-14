package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单实体类
 * </p>
 *
 * @author yt
 * @since 2022-01-13
 */
@Data
@ApiModel(description= "订单实体类")
@EqualsAndHashCode(callSuper = false)
public class TOrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @Schema(name = "dataId", description = "和商品dataid关联，填入下单商品的dataid", example = "5971a9fcb2c73a38a7ddd2718b3de8bf")
    private String dataId;

    /**
     * 订单号，uuid自动生成
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @Schema(hidden = true)
    private String orderId;

    /**
     * 下单时间
     */
    @Schema(name = "orderTime", description = "下单时间")
    private Date orderTime;

    /**
     * 下单用户
     */
    @Schema(name = "orderPerson", description = "下单用户")
    private String orderPerson;

}
