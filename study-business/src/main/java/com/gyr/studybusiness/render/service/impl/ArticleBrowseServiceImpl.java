package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyr.studybusiness.render.service.ArticleBrowseService;
import com.gyr.studycommon.dao.ArticleBrowseMapper;
import com.gyr.studycommon.entity.ArticleBrowse;
import com.gyr.studycommon.entity.UsersInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
@Service
public class ArticleBrowseServiceImpl implements ArticleBrowseService {

    @Autowired
    ArticleBrowseMapper articleBrowseMapper;

    @Override
    public List<ArticleBrowse> findBrowseQuantityByBlogId(Integer blogId) {
        QueryWrapper wrapper = new QueryWrapper();
        if(blogId!=null){
            wrapper.eq("blogId",blogId);
        }

        return articleBrowseMapper.selectList(wrapper);
    }

    @Override
    public List<ArticleBrowse> findBrowseQuantityByUserId(Integer userId) {
        QueryWrapper wrapper = new QueryWrapper();
        if(userId!=null){
            wrapper.eq("browseId",userId);
        }

        return articleBrowseMapper.selectList(wrapper);
    }
}
