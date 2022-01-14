package com.yt.seckill.entity.Dto;

import com.yt.seckill.validator.IsMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 应涛
 * @date 2021/11/21
 * @function：登录传参实体类
 */
@Data
@ApiModel(description= "登录传参实体类")
@NoArgsConstructor
@AllArgsConstructor
public class ParamUserDto {
    @NotNull(message = "手机号不能为空")
    @IsMobile
    @Schema(name = "tel", description = "登录方式（电话号码）", example = "13567886098")
    private String tel;

    @NotNull(message = "密码不能为空")
    @Schema(name = "passWord", description = "登录密码", example = "123qwe")
    private String passWord;
}
