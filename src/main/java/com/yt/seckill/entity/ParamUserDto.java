package com.yt.seckill.entity;

import com.yt.seckill.validator.IsMobile;
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
@NoArgsConstructor
@AllArgsConstructor
public class ParamUserDto {
    @NotNull(message = "手机号不能为空")
    @IsMobile
    private String tel;

    @NotNull(message = "密码不能为空")
    private String passWord;
}
