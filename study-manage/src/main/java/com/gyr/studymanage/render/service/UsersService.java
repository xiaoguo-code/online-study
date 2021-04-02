package com.gyr.studymanage.render.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.UsersVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface UsersService {
    /**
     * 根据条件获取用户信息列表（分页）
     * @param conditions
     * @return
     */
    PageUtils getUserListByCondition(UsersVO conditions);

    /**
     * 根据userId获取user信息
     * @param userId
     * @return
     */
    UsersInfo getAdminInfoById(Integer userId);

    /**
     * 新增管理员
     * @param usersInfo
     * @return
     */
    boolean save(UsersInfo usersInfo);

    /**
     * 更新管理员信息
     * @param usersInfo
     * @return
     */
    boolean update(UsersInfo usersInfo);

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param userIds
     * @return
     */
    boolean remove(String userIds);
}
