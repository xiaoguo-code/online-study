package com.gyr.studycommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @desc: 权限
 * @Author: guoyr
 * @Date: 2021-03-26 16:36
 */
@Data
@Component
public class Authority implements Serializable {
    /**
     * 标识
     */
    @TableId(value = "authorityId",type = IdType.AUTO)
    private Integer authorityId;
}
