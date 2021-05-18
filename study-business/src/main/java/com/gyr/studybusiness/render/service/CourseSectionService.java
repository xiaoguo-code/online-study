package com.gyr.studybusiness.render.service;

import com.gyr.studycommon.entity.CourseSectionInfo;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface CourseSectionService {

    /**
     * 根据课程id查询课程章节信息列表
     * @param courseId
     * @return
     */
    List<CourseSectionInfo> getCourseSectionListByType(String courseId);


    /**
     * 根据课程id查询课程章节信息列表
     * @param courseSectionInfo
     * @return
     */
    CourseSectionInfo getCourseSectionListByCondition(CourseSectionInfo courseSectionInfo);

    /**
     * 根据课程章节id获取课程章节信息
     * @param courseSectionId
     * @return
     */
    CourseSectionInfo getCourseSectionInfoById(Integer courseSectionId);

}
