package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 文章表
 */
@Data
@TableName("blog")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class Article  implements Serializable {
//    private static final long serialVersionUID = -2791508030874585618L;
    /**
     * 文章的主键编号
     */
    @TableId(value = "blogId",type = IdType.AUTO)
    private Integer blogId;
    /**
     * 内容标题
     */
    private String title;
//    /**
//     * 内容缩略名
//     */
//    private String slug;
    /**
     * 内容生成时的GMT unix时间戳
     */
    private String createTime;
    /**
     * 内容更改时的GMT unix时间戳
     */
    private String updateTime;

    private String publishTime;

    private String checkTime;
    /**
     * 内容文字
     */
    private String content;
    private String likes;
    /**
     * 内容所属用户id
     */
    private Integer authorId;

    private String courseSectionId;
    /**
     * 内容类别
     */
    private String blogType;
    /**
     * 内容状态
     */
    private String checkStatus;
    /**
     * 标签列表
     */
    private String tags;
    /**
     * 分类列表
     */
//    private String categories;
    private String isOpen;
    /**
     * 点击次数
     */
    private String views;
    /**
     * 内容所属评论数
     */
    private String comments;
    /**
     * 是否允许评论
     */
    private String allowComment;


}
