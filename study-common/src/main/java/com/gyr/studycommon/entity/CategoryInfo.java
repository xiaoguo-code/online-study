package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 20:52
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("category")
@Component
public class CategoryInfo extends Category{


    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 是否删除
     */
    private String isDelete;

    /**
     * 所有子级
     */
    @TableField(exist = false)
    private List<CategoryInfo> childrenList;

}
