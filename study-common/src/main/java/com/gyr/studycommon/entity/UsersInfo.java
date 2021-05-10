package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @desc: 用户信息
 * @Author: guoyr
 * @Date: 2021-03-26 16:21
 */
@Data
@TableName("users")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class UsersInfo extends Users{

    private Integer status;

    private String createTime;

    private String updateTime;

}
