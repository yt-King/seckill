package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * <p>
 *
 * </p>
 *
 * @author jobob
 * @since 2021-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * uuid
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String userId;
    /**
     * 姓名
     */
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "账号不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String passWord;

    /**
     * 身份证号
     */
    @NotNull(message = "身份证号不能为空")
    private String idCard;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private String birth;

    /**
     * 年龄
     */
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
    private String userType;

    /**
     * 联系电话
     */
    @NotNull(message = "联系电话不能为空")
    private String tel;


    /**
     * 状态 0:正常 1:异常(无业/失业)
     */
    private String status;

    /**
     * 是否管理员，0:否，1:是
     */
    private Boolean isLeader;

    /**
     * 创建记录时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 跟新记录时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
