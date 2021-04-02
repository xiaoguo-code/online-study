package com.gyr.studymanage.render.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @desc:
 * @Author: guoyr
 * @Date: 2021-03-14 18:51
 */
public interface AdminService {

    /**
     * 根据条件查询管理员列表（分页）
     * @param conditions
     * @return
     */
    PageUtils getAllAdminInfoList(AdminVO conditions);

    /**
     * 根据adminId获取admin信息
     * @param adminId
     * @return
     */
    AdminInfo getAdminInfoById(Integer adminId);

    /**
     * 新增管理员
     * @param adminInfo
     * @return
     */
    boolean save(AdminInfo adminInfo);

    /**
     * 更新管理员信息
     * @param adminInfo
     * @return
     */
    boolean update(AdminInfo adminInfo);

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     * @param AdminIds
     * @return
     */
    boolean remove(String AdminIds);
}
