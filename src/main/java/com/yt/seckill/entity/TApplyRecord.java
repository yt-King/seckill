package com.yt.seckill.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
@Data
@ApiModel(description= "申请记录实体类")
@EqualsAndHashCode(callSuper = false)
public class TApplyRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @Schema(hidden = true)
    private Long id;

    /**
     * 申请用户id
     */
    @Schema(name = "userId", description = "申请用户id", example = "7b0280e42f4e95f819cd0efc1067ce38")
    private String userId;

    /**
     * 申请用户姓名
     */
    @Schema(name = "userName", description = "申请用户姓名", example = "ytt")
    private String userName;

    /**
     * 申请商品id
     */
    @Schema(name = "goodsId", description = "申请商品id", example = "6f68b93342274956022285cb7336c00a")
    private String goodsId;

    /**
     * 申请商品名称
     */
    @Schema(name = "goodsName", description = "申请商品名称", example = "iPhone")
    private String goodsName;

    /**
     * 申请时间
     */
    @Schema(name = "applyTime", description = "申请时间", example = "2022-1-30 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date applyTime;

    /**
     * 申请结果 0：通过 ；1：不通过
     */
    @Schema(name = "applyResult", description = "申请结果 0：通过 ；1：不通过", example = "0")
    private Integer applyResult;

    /**
     * 逻辑删除  0:否，1:是
     */
    @Schema(hidden = true)
    private Integer isDelete;

    /**
     * 创建记录时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新记录时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
