package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc: 评论信息
 * @Author: guoyr
 * @Date: 2021-03-26 16:07
 */
@Data
@TableName("blog_comment")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class CommentInfo extends Comment{

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;

    @TableField(exist = false)
    private String sendName;

    @TableField(exist = false)
    private String receiveName;

    @TableField(exist = false)
    private String senderEmail;

    @TableField(exist = false)
    private Integer pageIndex;

    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 所有子级
     */
    @TableField(exist = false)
    private List<CommentInfo> childrenList;

}
