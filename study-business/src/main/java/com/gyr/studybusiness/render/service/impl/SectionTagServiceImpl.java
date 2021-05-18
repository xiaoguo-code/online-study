package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyr.studybusiness.render.service.SectionTagService;
import com.gyr.studycommon.dao.SectionTagMapper;
import com.gyr.studycommon.entity.SectionTagInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: 标签信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class SectionTagServiceImpl implements SectionTagService {

    @Autowired
    private SectionTagMapper sectionTagMapper;

    /**
     * 根据课程id查询课程章节信息列表
     *
     * @param sectionTagInfo
     * @return
     */
    @Override
    public List<SectionTagInfo> getSectionTagListByType(SectionTagInfo sectionTagInfo) {
        QueryWrapper wrapper = new QueryWrapper();
        List<SectionTagInfo> result = sectionTagMapper.selectList(wrapper);
        return result;
    }

    /**
     * 根据课程id查询课程章节信息列表
     *
     * @param sectionTagInfo
     * @return
     */
    @Override
    public SectionTagInfo getSectionTagByCondition(SectionTagInfo sectionTagInfo) {
        QueryWrapper wrapper = new QueryWrapper();

        return sectionTagMapper.selectOne(wrapper);

    }

    /**
     * 根据章节id获取标签
     *
     * @param sectionId
     * @return
     */
    @Override
    public SectionTagInfo getSectionTagInfoBySectionId(Integer sectionId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("sectionId",sectionId);
        return sectionTagMapper.selectOne(wrapper);
    }

    /**
     * 根据课程章节id获取课程章节信息
     *
     * @param sectionTagId
     * @return
     */
    @Override
    public SectionTagInfo getSectionTagInfoById(Integer sectionTagId) {
        SectionTagInfo sectionTagInfo = sectionTagMapper.selectById(sectionTagId);
        return sectionTagInfo;
    }

    /**
     * 新增课程章节信息
     *
     * @param sectionTagInfo
     * @return
     */
    @Override
    public boolean save(SectionTagInfo sectionTagInfo) {
        int i = sectionTagMapper.insert(sectionTagInfo);
        return i>0;
    }

    /**
     * 更新课程章节信息
     *
     * @param sectionTagInfo
     * @return
     */
    @Override
    public boolean update(SectionTagInfo sectionTagInfo) {
        int i = sectionTagMapper.updateById(sectionTagInfo);
        return i>0;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param sectionTagIds
     * @return
     */
    @Override
    public boolean remove(String sectionTagIds) {
        if (StringUtils.isNotBlank(sectionTagIds)){
            String[] ids = sectionTagIds.split(",");
            int i = sectionTagMapper.deleteBatchIds(Arrays.asList(ids));
            return i>0;
        }
        return false;
    }

    /**
     * 根据课程id删除标签
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
        int i = sectionTagMapper.deleteByMap(condition);
        return i>0;
    }
}
