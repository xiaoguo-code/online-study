package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @desc: 学习路线类
 * @Author: guoyr
 * @Date: 2021-05-03 18:42
 */
@Data
@Component
public class StudyLine implements Serializable {
    @TableId(value = "lineId",type = IdType.AUTO)
    private Integer lineId;

    private String categoryId;

    private String courseIds;

    private String courseNames;

}
