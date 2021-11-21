package com.yt.seckill.validator;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yt.seckill.utils.IdcardUtils;
import com.yt.seckill.utils.validatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 应涛
 * @date 2021/11/21
 * @function：
 */
public class IsIdCardValidator implements ConstraintValidator<IsIdCard, String> {
    private boolean required = true;

    @Override
    public void initialize(IsIdCard constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            if (StringUtils.isEmpty(value)) {
                return true;
            }
            switch (value.length()) {
                case 18:
                    if (!IdcardUtils.validateIdCard18(value)) {
                        return false;
                    }
                    break;
                case 15:
                    if (!IdcardUtils.validateIdCard15(value)) {
                        return false;
                    }
                    break;
                default:
                    if (IdcardUtils.validateIdCard10(value) == null) {
                        return false;
                    } else {
                        if (IdcardUtils.validateIdCard10(value)[2].equals("false")) {
                            return false;
                        }
                    }
            }
            return true;
        } else {
            if (StringUtils.isEmpty(value)) {
                return true;
            } else {
                switch (value.length()) {
                    case 18:
                        if (!IdcardUtils.validateIdCard18(value)) {
                            return false;
                        }
                        break;
                    case 15:
                        if (!IdcardUtils.validateIdCard15(value)) {
                            return false;
                        }
                        break;
                    default:
                        if (IdcardUtils.validateIdCard10(value) == null) {
                            return false;
                        } else {
                            if (IdcardUtils.validateIdCard10(value)[2].equals("false")) {
                                return false;
                            }
                        }
                }
                return true;
            }
        }
    }
}
