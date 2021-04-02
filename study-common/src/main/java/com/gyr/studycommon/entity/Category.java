package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @desc: 类别，分类 实体类
 * @Author: guoyr
 * @Date: 2021-03-14 18:10
 */
@Data
@Component
public class Category implements Serializable {

    /**
     * 类别标识
     */
    @TableId(value = "categoryId",type = IdType.AUTO)
    private Integer categoryId;

    /**
     * 类别名
     */
    @NotBlank(message = "title类别名不能为空")
    private String title;

    /**
     * 父类别id
     */
    private String parentId;

    /**
     * 类别编码
     */
    private String classCode;

    /**
     * 左孩子id
     */
    private String leftChild;

    /**
     * 又兄弟id
     */
    private String rightSibling;
    /**
     * 层级
     */
    private String layer;
}
