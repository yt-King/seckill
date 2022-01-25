package com.yt.seckill.entity;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(description = "商品风险引擎实体类")
@EqualsAndHashCode(callSuper = false)
public class TGoodsRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 和商品的Id关联
     */
    @NotNull(message = "商品id不能为空")
    @Schema(name = "dataId", description = "和商品dataid关联，填入下单商品的dataid", example = "27608e0c923d73dfb337235f36d7c6ae")
    private String dataId;

    /**
     * 年龄限制
     */
    @Schema(name = "limitAge", description = "参与活动的年龄限制", example = "18")
    private Integer limitAge;

    /**
     * 逾期计算起始时间
     */
    @Schema(name = "limitStartDate", description = "逾期计算起始时间", example = "2021-1-1 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date limitStartDate;

    /**
     * 逾期时长限制
     */
    @Schema(name = "limitOverdueTime", description = "参与活动的逾期时长限制，与逾期金额一起判断", example = "365")
    private Integer limitOverdueTime;

    /**
     * 逾期金额限制
     */
    @Schema(name = "limitOverdueNum", description = "参与活动的逾期金额限制", example = "3000")
    private Integer limitOverdueNum;

    /**
     * 逾期次数限制
     */
    @Schema(name = "limitOverdueFrequency", description = "逾期次数限制", example = "3")
    private Integer limitOverdueFrequency;

    /**
     * 是否失业限制，0：不限制； 1：限制，若失业无资格参与
     */
    @Schema(name = "limitIsUnemployment", description = "是否失业限制，0：不限制； 1：限制，若失业无资格参与", example = "0")
    private Integer limitIsUnemployment;

    /**
     * 是否失信限制，0：不限制； 1：限制，若失信无资格参与
     */
    @Schema(name = "limitIsBlack", description = "是否失信限制，0：不限制； 1：限制，若失信无资格参与", example = "0")
    private Integer limitIsBlack;

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
