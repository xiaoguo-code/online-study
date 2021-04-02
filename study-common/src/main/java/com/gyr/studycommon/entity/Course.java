package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @desc: 课程表
 * @Author: guoyr
 * @Date: 2021-03-26 15:59
 */
@Data
@Component
public class Course implements Serializable {

    /**
     * 课程标识
     */
    @TableId(value = "courseId",type = IdType.AUTO)
    private Integer courseId;

    /**
     * 课程所属类别
     */
    private String categoryIds;

    /**
     * 课程名
     */
    private String courseName;

    /**
     * 课程优先级（学习先后顺序）
     */
    private String priority;
}
