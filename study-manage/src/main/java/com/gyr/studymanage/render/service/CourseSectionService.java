package com.gyr.studymanage.render.service;

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

    /**
     * 新增课程章节信息
     * @param courseSectionInfo
     * @return
     */
    boolean save(CourseSectionInfo courseSectionInfo);

    /**
     * 更新课程章节信息
     * @param courseSectionInfo
     * @return
     */
    boolean update(CourseSectionInfo courseSectionInfo);

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param courseSectionIds
     * @return
     */
    boolean remove(String courseSectionIds);

    /**
     * 根据课程id删除章节
     * @param courseId
     * @return
     */
    boolean removeByCourseId(Integer courseId);
}
