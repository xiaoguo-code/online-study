package com.gyr.studymanage.render.service.impl;

import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studymanage.render.service.AutorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class AutorityServiceImpl implements AutorityService {


    /**
     * 根据父类别id获取所有层级类别信息
     *
     * @param parentId
     * @return
     */
    @Override
    public List<CategoryInfo> getChildCategoryByParentId(Integer parentId) {
        return null;
    }

    /**
     * 根据类别id获取类别信息
     *
     * @param categoryId
     * @return
     */
    @Override
    public CategoryInfo getCategoryInfoById(Integer categoryId) {
        return null;
    }

    /**
     * 新增类别信息
     *
     * @param categoryInfo
     * @return
     */
    @Override
    public boolean save(CategoryInfo categoryInfo) {
        return false;
    }

    /**
     * 更新类别信息
     *
     * @param categoryInfo
     * @return
     */
    @Override
    public boolean update(CategoryInfo categoryInfo) {
        return false;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param categoryIds
     * @return
     */
    @Override
    public boolean remove(String categoryIds) {
        return false;
    }
}
