package com.gyr.studybusiness.render.service;


import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.CategoryConditionVO;

import java.util.List;

/**
 * 类别service
 * @author guoyr
 */
public interface CategoryService {

    /**
     * 获取所有层级类别信息
     * @return
     */
    List<CategoryInfo> findAllCategory();

    /**
     * 获取第一层级类别信息
     * @return
     */
    List<CategoryInfo> getParentCategory(Integer parentId);

    List<CategoryInfo> getCategoryByCondition(CategoryConditionVO conditionVO);

    /**
     * 获取上一层级类别信息
     * @return
     */
    List<CategoryInfo> getPreParentCategory(Integer parentId);


    /**
     * 根据类别id获取类别信息
     * @param categoryId
     * @return
     */
    CategoryInfo getCategoryInfoById(Integer categoryId);

    /**
     * 返回id字符串   “1,2,3"
     * @param categoryId
     * @return
     */
    String getParentChildIdToStr(String categoryId);


}

