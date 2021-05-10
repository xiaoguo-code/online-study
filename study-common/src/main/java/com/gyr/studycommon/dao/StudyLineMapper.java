package com.gyr.studycommon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.StudyLineInfo;
import com.gyr.studycommon.vo.StudyLineInfoVO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
@Mapper
public interface StudyLineMapper extends BaseMapper<StudyLineInfo> {

}
