package com.gyr.studybusiness.render.service;


import com.gyr.studycommon.entity.Category;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.vo.CategoryVO;

import java.util.List;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-03-14 20:10:30
 */
public interface CategoryService {

    /*获取所有层级类别*/
    List<CategoryInfo> findAllCategory();
    /*获取第一层级类别*/

    /*获取第二层级类别*/

    /*添加类别*/
    boolean save(CategoryInfo category);
    /*更新类别*/
    boolean update(CategoryInfo category);
    /*移除类别*/
    boolean remove(int categoryId);


}

