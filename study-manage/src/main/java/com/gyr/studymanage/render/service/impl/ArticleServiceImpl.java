package com.gyr.studymanage.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studycommon.dao.AdminMapper;
import com.gyr.studycommon.dao.ArticleMapper;
import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.Article;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.SpecialUtil;
import com.gyr.studycommon.vo.AdminVO;
import com.gyr.studycommon.vo.ArticleVO;
import com.gyr.studymanage.render.service.AdminService;
import com.gyr.studymanage.render.service.ArticleService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;


    @Override
    public PageUtils getArticlePageByCondition(ArticleVO conditions) {
        if(conditions.getPageIndex()==null){
            conditions.setPageIndex(1);
        }
        if(conditions.getPageSize()==null){
            conditions.setPageSize(10);
        }
        //RowBounds rowBounds = new RowBounds(conditionVO.getPageSize()*conditionVO.getPageIndex()+1,conditionVO.getPageSize());
        IPage<Article> page = new Page<>(conditions.getPageIndex(),conditions.getPageSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        if(StringUtils.isNotBlank(conditions.getTitle())){
            wrapper.like("title",conditions.getTitle());
        }
        IPage<Article> iPage = articleMapper.selectPage(page, wrapper);
        return new PageUtils(iPage);
    }

    @Override
    public Article getArticleById(Integer blogId) {
        return articleMapper.selectById(blogId);
    }

    /**
     *
     * @param article
     * @return
     */
    @Override
    public boolean save(Article article) {

        return articleMapper.insert(article)>0;
    }

    /**
     *
     *
     * @param article
     * @return
     */
    @Override
    public boolean update(Article article) {
        return articleMapper.updateById(article)>0;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param AdminIds
     * @return
     */
    @Override
    public boolean remove(String AdminIds) {
        if(StringUtils.isNotBlank(AdminIds)){
            String[] idList = AdminIds.split(",");
            int i = articleMapper.deleteBatchIds(Arrays.asList(idList));
            return i>0;
        }
        return false;
    }
}
