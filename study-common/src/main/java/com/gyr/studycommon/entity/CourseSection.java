package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @desc: 课程章节
 * @Author: guoyr
 * @Date: 2021-03-26 16:04
 */
@Data
@Component
public class CourseSection implements Serializable {
    /**
     * 标识
     */
    @TableId(value = "sectionId",type = IdType.AUTO)
    private Integer sectionId;
    /**
     * 归属课程id
     */
    private Integer courseId;
    /**
     * 父章节id
     */
    private Integer parentSectionId;
    /**
     * 章节名
     */
    private String  sectionName;
    /**
     * 章节描述
     */
    private String  sectionDescribes;
    /**
     * 章节优先级顺序
     */
    private Integer priority;

}
