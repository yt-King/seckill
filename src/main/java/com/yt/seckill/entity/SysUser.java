package com.yt.seckill.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * uuid
     */
    private String userId;

    private String userName;

    private String passWord;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 0:有效，1:无效
     */
    private String lockMark;

    /**
     * 是否管理员，0:有效，1:无效
     */
    private Boolean isLeader;

    /**
     * 创建记录时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 跟新记录时间
     */
    private LocalDateTime gmtModified;


}
