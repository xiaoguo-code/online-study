package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @desc: 管理员表
 * @Author: guoyr
 * @Date: 2021-03-26 16:20
 */
@Data
public class Admin implements Serializable {

    /**
     * 管理员标识
     */
    @TableId(value = "adminId",type = IdType.AUTO)
    private Integer adminId;

    /**
     * 管理员名
     */
    private String adminName;

    /**
     * 管理员真实姓名
     */
    private String adminRealName;

    /**
     * 管理员密码
     */
    private String adminPassword;
}
