package com.gyr.studycommon.vo;

import com.gyr.studycommon.entity.CourseInfo;
import lombok.Data;

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
    private Integer currPage;

}
