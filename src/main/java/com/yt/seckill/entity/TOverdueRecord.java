package com.yt.seckill.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 
 * </p>
 *
 * @author yt
 * @since 2022-01-24
 */
@Data
@ApiModel(description = "逾期记录实体类")
@EqualsAndHashCode(callSuper = false)
public class TOverdueRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @Schema(hidden = true)
    @TableId(type = IdType.ASSIGN_UUID)
    private String dataId;

    /**
     * 逾期账号
     */
    @NotNull(message = "逾期用户账号不能为空")
    @Schema(name = "overdueId", description = "逾期账号", example = "13567886039")
    private String overdueId;

    /**
     * 逾期金额
     */
    @Schema(name = "overdueNum", description = "逾期金额", example = "5000")
    private Integer overdueNum;

    /**
     * 逾期日期
     */
    @Schema(name = "overdueDate", description = "逾期日期", example = "2021-1-8 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date overdueDate;

    /**
     * 逾期时长
     */
    @Schema(name = "overdueTime", description = "逾期时长", example = "300")
    private Integer overdueTime;

    /**
     * 是否失信  0:否，1:是
     */
    @Schema(name = "isBreak", description = "是否失信", example = "1")
    private Integer isBreak;

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
