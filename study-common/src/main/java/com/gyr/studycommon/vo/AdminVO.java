package com.gyr.studycommon.vo;

import com.gyr.studycommon.entity.AdminInfo;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @desc: 管理员VO
 * @Author: guoyr
 * @Date: 2021-03-26 17:03
 */
@Data
public class AdminVO extends AdminInfo {

    private Integer pageIndex;

    private Integer pageSize;
}
