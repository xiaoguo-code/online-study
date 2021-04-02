package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @desc: 课程信息
 * @Author: guoyr
 * @Date: 2021-03-26 16:01
 */
@Data
@TableName("course")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class CourseInfo extends Course {

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 是否删除
     */
    @TableField(exist = false)
    private String isDelete;


}
