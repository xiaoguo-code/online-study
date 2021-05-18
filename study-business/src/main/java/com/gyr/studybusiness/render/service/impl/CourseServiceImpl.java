package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studybusiness.render.service.CourseService;
import com.gyr.studycommon.dao.CourseMapper;
import com.gyr.studycommon.entity.CourseInfo;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CourseVO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class CourseServiceImpl implements CourseService {


    @Autowired
    private CourseMapper courseMapper;

//    @Autowired
//    private CourseSectionService courseSectionService ;
//
//    @Autowired
//    private SectionTagService sectionTagService;

    /**
     * 根据条件查询课程信息列表（分页）
     *
     * @param conditions
     * @return
     */
    @Override
    public PageUtils getCoursesListByCondition(CourseVO conditions) {
        if (conditions.getPageIndex()==null) {
            conditions.setPageIndex(1);
        }
        if (conditions.getPageSize()==null){
            conditions.setPageSize(10);
        }
        IPage<CourseInfo> page = new Page<>(conditions.getPageIndex(),conditions.getPageSize());
        QueryWrapper<CourseInfo> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(conditions.getCategoryIds())){
            wrapper.like("categoryIds",conditions.getCategoryIds()+",");
        }
        if (StringUtils.isNotBlank(conditions.getCourseName())){
            wrapper.like("courseName",conditions.getCourseName());
        }
        if(StringUtils.isNotBlank(conditions.getPriority())){
            wrapper.eq("priority",conditions.getPriority());
        }
        IPage<CourseInfo> iPage = courseMapper.selectPage(page, wrapper);
        return new PageUtils(iPage);
    }

    /**
     * 根据课程类别查询课程信息列表
     *
     * @param courseType 如："1,2,3"
     * @return
     */
    @Override
    public List<CourseInfo> getCoursesListByType(String courseType) {
        QueryWrapper<CourseInfo> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(courseType)){
            wrapper.eq("categoryIds",courseType);
        }
        List<CourseInfo> courseInfos = courseMapper.selectList(wrapper);
        return courseInfos;
    }

    /**
     * 根据课程id获取课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfo getCoursesInfoById(Integer courseId) {
        CourseInfo courseInfo = courseMapper.selectById(courseId);
        return courseInfo;
    }

    /**
     * 根据课程id获取课程信息
     *
     * @return
     */
    @Override
    public CourseInfo getCoursesInfoByCondition(String categoryIds,String courseName) {
        QueryWrapper wrapper = new QueryWrapper();

        if (StringUtils.isNotBlank(categoryIds)){
            wrapper.like("categoryIds",categoryIds);
        }
        if (StringUtils.isNotBlank(courseName)){
            wrapper.eq("courseName",courseName);
        }
        CourseInfo courseInfo = courseMapper.selectOne(wrapper);
        return courseInfo;
    }

    /**
     * 根据条件获取课程信息
     *
     * @param courseInfo
     * @return
     */
    @Override
    public CourseInfo getCoursesInfoByCondition(CourseInfo courseInfo) {
        QueryWrapper wrapper = new QueryWrapper();

        if (StringUtils.isNotBlank(courseInfo.getCategoryIds())){
            wrapper.like("categoryIds",courseInfo.getCategoryIds());
        }
        if (StringUtils.isNotBlank(courseInfo.getCourseName())){
            wrapper.eq("courseName",courseInfo.getCategoryIds());
        }
        return courseMapper.selectOne(wrapper);
    }

}
