package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class TOrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId
    private String dataId;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 下单时间
     */
    private Date orderTime;

    /**
     * 下单用户
     */
    private String orderPerson;

}
