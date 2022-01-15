package com.yt.seckill.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yt.seckill.validator.IsMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 应涛
 * @date 2022/1/15
 * @function：临时订单传参实体类
 */
@Data
@ApiModel(description= "临时订单传参实体类")
@NoArgsConstructor
@AllArgsConstructor
public class ParamTempDto {
    /**
     * 商品id
     */
    @Schema(name = "goodsId", description = "商品id", example = "27608e0c923d73dfb337235f36d7c6ae")
    private String goodsId;

    /**
     * 用户id
     */
    @Schema(name = "userId", description = "用户id", example = "92984cc329c2d5ea9620caa2839ef3c6")
    private String userId;

    /**
     * 校验码
     */
    @Schema(name = "verifyHash", description = "校验码")
    private String verifyHash;
}
