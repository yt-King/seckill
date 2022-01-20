package com.yt.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yt
 * @since 2022-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户权限实体类", description="")
public class SysPermissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限表id 作为表主键 用于关联")
    @TableId("perId")
    private String perId;

    @ApiModelProperty(value = "权限名称")
    @TableField("permissionsName")
    private String permissionsName;

    @ApiModelProperty(value = "备注，预留字段")
    @TableField("perRemarks")
    private String perRemarks;


}
