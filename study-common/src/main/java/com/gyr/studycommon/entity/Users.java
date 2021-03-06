package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @desc: 用户
 * @Author: guoyr
 * @Date: 2021-03-26 16:21
 */
@Data
@Component
public class Users implements Serializable {
    /**
     * 标识
     */
    @TableId(value = "userId",type = IdType.AUTO)
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 邮箱
     */
    private String telPhone;
    /**
     * 真是姓名
     */
    private String realName;
    /**
     * 身份证
     */
    private String IDCard;
    /**
     * 性别
     */
    private String gender;
    /**
     * 年龄
     */
    private String age;


}
