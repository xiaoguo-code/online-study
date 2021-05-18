package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyr.studybusiness.render.service.CourseSectionService;
import com.gyr.studycommon.dao.CourseSectionMapper;
import com.gyr.studycommon.entity.CourseSectionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class CourseSectionServiceImpl implements CourseSectionService {

    @Autowired
    private CourseSectionMapper courseSectionMapper;

    /**
     * 根据课程id查询课程章节信息列表
     *
     * @param courseId
     * @return
     */
    @Override
    public List<CourseSectionInfo> getCourseSectionListByType(String courseId) {
        QueryWrapper wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(courseId)){
            wrapper.eq("courseId",courseId);
        }
        List<CourseSectionInfo> result = courseSectionMapper.selectList(wrapper);
        return result;
    }

    /**
     * 根据课程id查询课程章节信息列表
     *
     * @param courseSectionInfo
     * @return
     */
    @Override
    public CourseSectionInfo getCourseSectionListByCondition(CourseSectionInfo courseSectionInfo) {
        QueryWrapper wrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(courseSectionInfo.getSectionName())){
            wrapper.eq("sectionName",courseSectionInfo.getSectionName());
        }
        if(courseSectionInfo.getCourseId()==null){
            wrapper.eq("courseId",courseSectionInfo.getCourseId());
        }
        if (courseSectionInfo.getPriority()==null){
            wrapper.eq("priority",courseSectionInfo.getPriority());
        }
        return courseSectionMapper.selectOne(wrapper);

    }

    /**
     * 根据课程章节id获取课程章节信息
     *
     * @param courseSectionId
     * @return
     */
    @Override
    public CourseSectionInfo getCourseSectionInfoById(Integer courseSectionId) {
        CourseSectionInfo courseSectionInfo = courseSectionMapper.selectById(courseSectionId);
        return courseSectionInfo;
    }


}
