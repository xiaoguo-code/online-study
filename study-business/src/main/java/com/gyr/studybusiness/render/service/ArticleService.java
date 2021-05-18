package com.gyr.studybusiness.render.service;

import com.gyr.studycommon.entity.Article;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.ArticleVO;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface ArticleService {


    PageUtils getArticlePageByCondition(ArticleVO conditions);

    Article getArticleById(Integer blogId);

    /**
     * 新增管理员
     * @param article
     * @return
     */
    boolean save(Article article);

    /**
     * 更新管理员信息
     * @param article
     * @return
     */
    boolean update(Article article);

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param ids
     * @return
     */
    boolean remove(String ids);
}
