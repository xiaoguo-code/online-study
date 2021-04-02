package com.gyr.studymanage.render.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyr.studycommon.entity.CategoryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
@Mapper
public interface AutorityService {
    /**
     * 根据父类别id获取所有层级类别信息
     * @param parentId
     * @return
     */
    List<CategoryInfo> getChildCategoryByParentId(Integer parentId);

    /**
     * 根据类别id获取类别信息
     * @param categoryId
     * @return
     */
    CategoryInfo getCategoryInfoById(Integer categoryId);

    /**
     * 新增类别信息
     * @param categoryInfo
     * @return
     */
    boolean save(CategoryInfo categoryInfo);

    /**
     * 更新类别信息
     * @param categoryInfo
     * @return
     */
    boolean update(CategoryInfo categoryInfo);

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param categoryIds
     * @return
     */
    boolean remove(String categoryIds);
}
