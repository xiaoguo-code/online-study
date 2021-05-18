package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 文章浏览次数
 */
@Data
@TableName("blog_browse")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
public class ArticleBrowse implements Serializable {
//    private static final long serialVersionUID = -2791508030874585618L;
    /**
     * 文章的主键编号
     */
//    @TableId(value = "blogId",type = IdType.AUTO)
    private Integer blogId;
    /**
     * 内容标题
     */
    private String browseId;

    /**
     * 内容生成时的GMT unix时间戳
     */
    private String browseTime;

}
