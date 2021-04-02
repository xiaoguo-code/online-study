package com.gyr.studycommon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
@Mapper
public interface AdminMapper extends BaseMapper<AdminInfo> {

}
