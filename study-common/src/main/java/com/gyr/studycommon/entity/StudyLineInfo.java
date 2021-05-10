package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @desc: 学习路线类
 * @Author: guoyr
 * @Date: 2021-05-03 18:42
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("study_line")
@Component
public class StudyLineInfo extends StudyLine {


    private String createTime;

    private String updateTime;

}
