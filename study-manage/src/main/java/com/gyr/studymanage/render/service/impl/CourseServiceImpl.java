package com.gyr.studymanage.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studycommon.dao.CourseMapper;
import com.gyr.studycommon.entity.CourseInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CourseVO;
import com.gyr.studymanage.render.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    /**
     * 根据条件查询课程信息列表（分页）
     *
     * @param conditions
     * @return
     */
    @Override
    public PageUtils getCoursesListByCondition(CourseVO conditions) {
        if (conditions.getCurrPage()==null) {
            conditions.setCurrPage(1);
        }
        if (conditions.getPageSize()==null){
            conditions.setPageSize(3);
        }
        IPage<CourseInfo> page = new Page<>(conditions.getCurrPage(),conditions.getPageSize());
        QueryWrapper<CourseInfo> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(conditions.getCategoryIds())){
            wrapper.eq("categoryIds",conditions.getCategoryIds());
        }
        if (StringUtils.isNotBlank(conditions.getCourseName())){
            wrapper.like("courseName",conditions.getCourseName());
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
     * 新增课程信息
     *
     * @param courseInfo
     * @return
     */
    @Override
    public boolean save(CourseInfo courseInfo) {
        int result = courseMapper.insert(courseInfo);
        return result>0;
    }

    /**
     * 更新课程信息
     *
     * @param courseInfo
     * @return
     */
    @Override
    public boolean update(CourseInfo courseInfo) {
        int result = courseMapper.updateById(courseInfo);
        return result>0;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param courseIds
     * @return
     */
    @Override
    public boolean remove(String courseIds) {
        if(StringUtils.isNotBlank(courseIds)){
            String[] idList = courseIds.split(",");
            int i = courseMapper.deleteBatchIds(Arrays.asList(idList));
            return i>0;
        }
        return false;
    }
}
