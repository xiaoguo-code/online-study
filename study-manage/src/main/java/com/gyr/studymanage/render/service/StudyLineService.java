package com.gyr.studymanage.render.service;


import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.StudyLine;
import com.gyr.studycommon.entity.StudyLineInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CategoryConditionVO;
import com.gyr.studycommon.vo.StudyLineInfoVO;

import java.util.List;

/**
 * 学习路线管理service
 * @author guoyr
 */
public interface StudyLineService {


    /**
     * 根据阶段id获取该阶段学习路线
     * @param categoryId
     * @return
     */
    StudyLineInfo getStudyLineByCategoryId(String categoryId);

    StudyLineInfo getStudyLineById(Integer lineId);

    boolean save(StudyLineInfo studyLineInfo);

    boolean update(StudyLineInfo studyLineInfo);

    boolean delete(String studyLineIds);

}

