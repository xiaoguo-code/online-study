package com.gyr.studycommon.vo;


import com.gyr.studycommon.entity.Category;
import com.gyr.studycommon.entity.CategoryInfo;
import lombok.Data;

import java.util.List;

/**
 * @desc: 类别信息相应类
 * @Author: guoyr
 * @Date: 2021-03-14 18:12
 */

@Data
public class CategoryVO extends CategoryInfo {

    /**
     * 所有子级
     */
    private List<CategoryInfo> childrenList;
}
