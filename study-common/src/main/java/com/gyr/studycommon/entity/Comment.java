package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @desc: 评论
 * @Author: guoyr
 * @Date: 2021-03-26 16:06
 */
@Data
@Component
public class Comment implements Serializable {
    /**
     * 标识
     */
    @TableId(value = "commentId",type = IdType.AUTO)
    private Integer commentId;
    /**
     * 归属章节id
     */
    private Integer courseSectionId;
    /**
     * 归属文章id
     */
    private Integer blogId;
    /**
     * 归属主评论id
     */
    private Integer commentParentId;
    /**
     * 发送回复的用户id
     */
    private Integer sendId;

    /**
     * 接受回复的用户id
     */
    private Integer receiveId;

    /**
     * 评论内容
     */
    private String content;
}
