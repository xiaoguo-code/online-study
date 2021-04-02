package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @desc: 课程章节
 * @Author: guoyr
 * @Date: 2021-03-26 16:04
 */
@Data
@TableName("course_section")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class CourseSectionInfo extends CourseSection{

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

}
