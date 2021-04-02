package com.gyr.studymanage.render.service.impl;

import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.AdminVO;
import com.gyr.studymanage.render.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {


    /**
     * 根据条件查询管理员列表（分页）
     *
     * @param conditions
     * @return
     */
    @Override
    public PageUtils getAllAdminInfoList(AdminVO conditions) {
        return null;
    }

    /**
     * 根据adminId获取admin信息
     *
     * @param adminId
     * @return
     */
    @Override
    public AdminInfo getAdminInfoById(Integer adminId) {
        return null;
    }

    /**
     * 新增管理员
     *
     * @param adminInfo
     * @return
     */
    @Override
    public boolean save(AdminInfo adminInfo) {
        return false;
    }

    /**
     * 更新管理员信息
     *
     * @param adminInfo
     * @return
     */
    @Override
    public boolean update(AdminInfo adminInfo) {
        return false;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param AdminIds
     * @return
     */
    @Override
    public boolean remove(String AdminIds) {
        return false;
    }
}
