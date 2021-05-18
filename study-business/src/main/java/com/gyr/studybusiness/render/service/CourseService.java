package com.gyr.studybusiness.render.service;

import com.gyr.studycommon.entity.CourseInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CourseVO;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface CourseService {
    /**
     * 根据条件查询课程信息列表（分页）
     * @param conditions
     * @return
     */
    PageUtils getCoursesListByCondition(CourseVO conditions);



    /**
     * 根据课程类别查询课程信息列表
     * @param courseType   如："1,2,3"
     * @return
     */
    List<CourseInfo> getCoursesListByType(String courseType);

    /**
     * 根据课程id获取课程信息
     * @param courseId
     * @return
     */
    CourseInfo getCoursesInfoById(Integer courseId);

    /**
     * 根据条件获取课程信息
     * @param categoryIds
     * @param courseName
     * @return
     */
    CourseInfo getCoursesInfoByCondition(String categoryIds,String courseName);

    /**
     * 根据条件获取课程信息
     * @param courseInfo
     * @return
     */
    CourseInfo getCoursesInfoByCondition(CourseInfo courseInfo);


}
