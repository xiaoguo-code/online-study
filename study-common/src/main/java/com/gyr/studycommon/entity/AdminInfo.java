package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc: 管理员信息
 * @Author: guoyr
 * @Date: 2021-03-26 16:20
 */
@Data
@TableName("admin")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AdminInfo extends Admin{

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 角色
     */
    private String role;
    /**
     * 权限
     */
    @TableField(exist = false)
    private List<Authority> authorityList;

}
