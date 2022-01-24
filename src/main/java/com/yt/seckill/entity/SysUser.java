package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.yt.seckill.validator.IsIdCard;
import com.yt.seckill.validator.IsMobile;
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author yt
 * @since 2021-11-20
 */
@Data
@ApiModel(description = "用户实体类")
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(hidden = true)
    private Long id;

    /**
     * uuid
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @Schema(hidden = true)
    private String userId;

    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    @Schema(name = "name", description = "姓名", example = "jack")
    private String name;

    /**
     * 通过手机号注册
     */
    @NotNull(message = "手机号不能为空")
    @IsMobile
    @Schema(name = "tel", description = "手机号（需通过校验）", example = "19858873692")
    private String tel;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Schema(name = "passWord", description = "密码（不能为空）", example = "123456789")
    private String passWord;

    /**
     * 身份证号
     */
    @NotNull(message = "身份证号不能为空")
    @IsIdCard
    @Schema(name = "idCard", description = "身份证号（不能为空，需通过校验）", example = "330227200108111356")
    private String idCard;

    /**
     * 性别，根据生证份得出
     */
    @Schema(hidden = true)
    private String sex;

    /**
     * 生日，根据生证份得出
     */
    @Schema(hidden = true)
    private String birth;

    /**
     * 年龄，根据生证份得出
     */
    @Schema(hidden = true)
    private String age;

    /**
     * 用户类型
     * ①企业法人；
     * ②企业法人内部单位核算的单位；
     * ③管理财政预算资金和预算外资金的财政部门；　④实行财政管理的行政机关、事业单位；
     * ⑤县级（含）以上军队、武警单位；
     * ⑥外国驻华机构；
     * ⑦社会团体；
     * ⑧单位附属的食堂、招待所、幼儿园；
     * ⑨外地常设机构；
     * ⑩私营企业、个体经营承包户和个人。
     */
    @Schema(name = "userType", description = "* 用户类型\n" +
            "     * ①企业法人；\n" +
            "     * ②企业法人内部单位核算的单位；\n" +
            "     * ③管理财政预算资金和预算外资金的财政部门；　④实行财政管理的行政机关、事业单位；\n" +
            "     * ⑤县级（含）以上军队、武警单位；\n" +
            "     * ⑥外国驻华机构；\n" +
            "     * ⑦社会团体；\n" +
            "     * ⑧单位附属的食堂、招待所、幼儿园；\n" +
            "     * ⑨外地常设机构；\n" +
            "     * ⑩私营企业、个体经营承包户和个人。", example = "10")
    private String userType;

    /**
     * 所在省份，根据生证份得出
     */
    @Schema(hidden = true)
    private String address;


    /**
     * 状态 0:正常 1:异常(无业/失业)
     */
    @Schema(name = "status", description = "状态 0:正常 1:异常(无业/失业)", example = "0")
    private String status;

    /**
     * 是否失信，0:否，1:是
     */
    @Schema(hidden = true)
    private Boolean isBlack;

    /**
     * 逻辑删除，0:否，1:是
     */
    @Schema(hidden = true)
    private Boolean isDelete;

    /**
     * 是否活跃，0:否，1:是
     */
    @Schema(hidden = true)
    private Boolean isActivity;

    /**
     * 创建记录时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 跟新记录时间
     */
    @Schema(hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
