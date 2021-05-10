package com.gyr.studycommon.vo;

import com.gyr.studycommon.entity.UsersInfo;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @desc: 用户VO
 * @Author: guoyr
 * @Date: 2021-03-26 17:20
 */
@Data
public class UsersVO extends UsersInfo {

    private Integer pageIndex;

    private Integer pageSize;
}
