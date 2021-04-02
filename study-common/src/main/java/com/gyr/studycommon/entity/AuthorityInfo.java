package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @desc: 权限信息
 * @Author: guoyr
 * @Date: 2021-03-26 16:36
 */
@Data
@TableName("authority")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class AuthorityInfo extends Authority{

}
