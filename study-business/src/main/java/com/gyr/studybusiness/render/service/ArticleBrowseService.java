package com.gyr.studybusiness.render.service;

import com.gyr.studycommon.entity.ArticleBrowse;
import com.gyr.studycommon.entity.UsersInfo;
import io.swagger.models.auth.In;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface ArticleBrowseService {

    List<ArticleBrowse> findBrowseQuantityByBlogId(Integer blogId);

    List<ArticleBrowse> findBrowseQuantityByUserId(Integer userId);

}
