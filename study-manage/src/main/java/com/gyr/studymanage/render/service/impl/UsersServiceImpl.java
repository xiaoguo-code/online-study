package com.gyr.studymanage.render.service.impl;

import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.UsersVO;
import com.gyr.studymanage.render.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    /**
     * 根据条件获取用户信息列表（分页）
     *
     * @param conditions
     * @return
     */
    @Override
    public PageUtils getUserListByCondition(UsersVO conditions) {
        return null;
    }

    /**
     * 根据userId获取user信息
     *
     * @param userId
     * @return
     */
    @Override
    public UsersInfo getAdminInfoById(Integer userId) {
        return null;
    }

    /**
     * 新增管理员
     *
     * @param usersInfo
     * @return
     */
    @Override
    public boolean save(UsersInfo usersInfo) {
        return false;
    }

    /**
     * 更新管理员信息
     *
     * @param usersInfo
     * @return
     */
    @Override
    public boolean update(UsersInfo usersInfo) {
        return false;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param userIds
     * @return
     */
    @Override
    public boolean remove(String userIds) {
        return false;
    }
}
