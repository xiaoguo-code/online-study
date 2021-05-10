package com.gyr.studycommon.vo;

import com.gyr.studycommon.entity.Article;
import lombok.Data;

/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-05-11 0:35
 */
@Data
public class ArticleVO extends Article {

    private Integer pageIndex;

    private Integer pageSize;
}
