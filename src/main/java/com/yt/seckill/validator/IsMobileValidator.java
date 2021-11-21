package com.yt.seckill.validator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yt.seckill.utils.validatorUtil;
import org.hibernate.validator.cfg.context.Constrainable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 应涛
 * @date 2021/11/21
 * @function：
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {
    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            if (StringUtils.isEmpty(value)) {
                return true;
            }
            return validatorUtil.isMobile(value);
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                return validatorUtil.isMobile(value);
            }
        }
    }
}
