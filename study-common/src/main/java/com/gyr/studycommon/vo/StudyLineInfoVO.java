package com.gyr.studycommon.vo;


import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.Course;
import com.gyr.studycommon.entity.StudyLineInfo;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

/**
 * @desc: 类别信息相应类
 * @Author: guoyr
 * @Date: 2021-03-14 18:12
 */

@Data
public class StudyLineInfoVO extends StudyLineInfo {

    /**
     * java,c语言。。。
     */
    private String  categoryName;

    /**
     * 阶段名
     */
    private String  levelNames;

    /**
     * 阶段id
     */
    private Integer levelId;

    /**
     * 学习路线对应的课程信息
     */
    private List<Course> courseList;
}
