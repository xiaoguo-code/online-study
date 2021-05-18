package com.gyr.studybusiness.render.service;

import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.AdminVO;
import io.swagger.models.auth.In;
import org.apache.catalina.User;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface UserService {

    UsersInfo login(UsersInfo userInfo);

    UsersInfo getUserInfoLogin(UsersInfo userInfo);

    boolean update(UsersInfo usersInfo);

    UsersInfo getUserInfoByEmail(String email);

    boolean save(UsersInfo usersInfo);

    UsersInfo getUserInfoById(Integer userId );

}
