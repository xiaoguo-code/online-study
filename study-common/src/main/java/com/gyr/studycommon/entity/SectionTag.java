package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @desc: 章节对应的标签
 * @Author: guoyr
 * @Date: 2021-05-06 21:51
 */

@Data
@Component
public class SectionTag {

    /**
     * 标签id
     */
    @TableId(value = "sectionTagId",type = IdType.AUTO)
    private Integer sectionTagId;

    /**
     * 标签归属类别
     */
    private String categoryIds;

    /**
     * 标签归属课程id
     */
    private Integer courseId;

    /**
     * 标签归属课程章节id
     */
    private Integer sectionId;

    /**
     * 标签名
     */
    private String tagName;


}
