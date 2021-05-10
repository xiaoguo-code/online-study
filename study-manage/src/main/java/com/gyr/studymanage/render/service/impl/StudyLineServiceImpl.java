package com.gyr.studymanage.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studycommon.dao.CategoryMapper;
import com.gyr.studycommon.dao.StudyLineMapper;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.StudyLineInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CategoryConditionVO;
import com.gyr.studymanage.render.service.CategoryService;
import com.gyr.studymanage.render.service.StudyLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class StudyLineServiceImpl implements StudyLineService {

    @Autowired
    private StudyLineMapper studyLineMapper;


    /**
     * 根据阶段id获取该阶段学习路线
     *
     * @param categoryId
     * @return
     */
    @Override
    public StudyLineInfo getStudyLineByCategoryId(String categoryId) {
        QueryWrapper wrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(categoryId)){
            wrapper.eq("categoryId",categoryId);
        }
        StudyLineInfo studyLineInfo = studyLineMapper.selectOne(wrapper);
        return studyLineInfo;
    }

    @Override
    public StudyLineInfo getStudyLineById(Integer lineId) {

        return studyLineMapper.selectById(lineId);
    }

    @Override
    public boolean save(StudyLineInfo studyLineInfo) {
        int insert = studyLineMapper.insert(studyLineInfo);
        return insert>0;
    }

    @Override
    public boolean update(StudyLineInfo studyLineInfo) {
        return studyLineMapper.updateById(studyLineInfo)>0;
    }

    @Override
    public boolean delete(String studyLineIds) {
        if(StringUtils.isNotBlank(studyLineIds)){
            String[] idList = studyLineIds.split(",");
            int i = studyLineMapper.deleteBatchIds(Arrays.asList(idList));
            return i>0;
        }
        return false;
    }
}
