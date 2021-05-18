package com.gyr.studycommon.vo;

import com.gyr.studycommon.entity.CourseInfo;
import com.gyr.studycommon.entity.CourseSectionInfo;
import com.gyr.studycommon.entity.SectionTagInfo;
import lombok.Data;

import java.util.List;

/**
 * @desc: 用户VO
 * @Author: guoyr
 * @Date: 2021-03-26 17:20
 */
@Data
public class CourseVO extends CourseInfo {

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 当前页数
     */
    private Integer pageIndex;

    /**
     * 归属阶段名
     */
    private  String levelName;

    /**
     * 课程对应的章节
     */
    private String courseSectionNames;

    /**
     * 章节对应的描述
     */
    private String sectionDescribes;

    /**
     * 课程对应章节所对应的标签
     */
    private String courseSectionTagNames;

    /**
     * 课程对应的章节
     */
    private String sectionIdValues;

    /**
     * 课程对应章节所对应的标签
     */
    private String sectionTagIdValues;


}
