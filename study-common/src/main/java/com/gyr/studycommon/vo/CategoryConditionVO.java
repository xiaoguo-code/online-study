package com.gyr.studycommon.vo;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @desc: 分类查询条件封装
 * @Author: guoyr
 * @Date: 2021-03-31 22:01
 */
@Data
public class CategoryConditionVO {

    private Integer parentId;

    private String title;

    private Integer pageIndex;

    private Integer pageSize;

}
