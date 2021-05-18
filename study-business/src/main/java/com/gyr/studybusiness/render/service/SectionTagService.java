package com.gyr.studybusiness.render.service;

import com.gyr.studycommon.entity.SectionTagInfo;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface SectionTagService {

    /**
     * 根据课程id查询课程章节信息列表
     * @param sectionTagInfo
     * @return
     */
    List<SectionTagInfo> getSectionTagListByType(SectionTagInfo sectionTagInfo);


    /**
     * 根据条件获取标签对象
     * @param sectionTagInfo
     * @return
     */
    SectionTagInfo getSectionTagByCondition(SectionTagInfo sectionTagInfo);

    /**
     * 根据章节id获取标签
     * @param sectionId
     * @return
     */
    SectionTagInfo getSectionTagInfoBySectionId(Integer sectionId);

    /**
     * 根据id标签信息
     * @param sectionTagId
     * @return
     */
    SectionTagInfo getSectionTagInfoById(Integer sectionTagId);

    /**
     * 新增标签信息
     * @param sectionTagInfo
     * @return
     */
    boolean save(SectionTagInfo sectionTagInfo);

    /**
     * 更新标签信息
     * @param sectionTagInfo
     * @return
     */
    boolean update(SectionTagInfo sectionTagInfo);

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param sectionTagIds
     * @return
     */
    boolean remove(String sectionTagIds);

    /**
     * 根据课程id删除标签
     * @param courseId
     * @return
     */
    boolean removeByCourseId(Integer courseId);
}
