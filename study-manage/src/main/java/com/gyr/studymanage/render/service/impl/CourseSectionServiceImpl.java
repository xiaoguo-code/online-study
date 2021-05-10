package com.gyr.studymanage.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyr.studycommon.dao.CourseSectionMapper;
import com.gyr.studycommon.entity.CourseSectionInfo;
import com.gyr.studymanage.render.service.CourseSectionService;
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

    /**
     * 新增课程章节信息
     *
     * @param courseSectionInfo
     * @return
     */
    @Override
    public boolean save(CourseSectionInfo courseSectionInfo) {
        int i = courseSectionMapper.insert(courseSectionInfo);
        return i>0;
    }

    /**
     * 更新课程章节信息
     *
     * @param courseSectionInfo
     * @return
     */
    @Override
    public boolean update(CourseSectionInfo courseSectionInfo) {
        int i = courseSectionMapper.updateById(courseSectionInfo);
        return i>0;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param courseSectionIds
     * @return
     */
    @Override
    public boolean remove(String courseSectionIds) {
        if (StringUtils.isNotBlank(courseSectionIds)){
            String[] ids = courseSectionIds.split(",");
            int i = courseSectionMapper.deleteBatchIds(Arrays.asList(ids));
            return i>0;
        }
        return false;
    }

    /**
     * 根据课程id删除章节
     *
     * @param courseId
     * @return
     */
    @Override
    public boolean removeByCourseId(Integer courseId) {
        Map<String,Object> condition = new HashMap<>();
        if(courseId!=null){
            condition.put("courseId",courseId);
        }
        int i = courseSectionMapper.deleteByMap(condition);
        return i>0;
    }
}
