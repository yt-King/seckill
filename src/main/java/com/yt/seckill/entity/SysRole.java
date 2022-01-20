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
@ApiModel(value="用户角色实体类", description="")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = " 角色id 作为表主键 用于关联")
    @TableId("roleId")
    private String roleId;

    @ApiModelProperty(value = "角色名")
    @TableField("roleName")
    private String roleName;

    @ApiModelProperty(value = "备注，预留字段")
    @TableField("roleRemarks")
    private String roleRemarks;


}
