package com.gyr.studymanage.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studycommon.dao.AdminMapper;
import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.SpecialUtil;
import com.gyr.studycommon.vo.AdminVO;
import com.gyr.studymanage.render.service.AdminService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @desc: 课程信息service
 * @Author: guoyr
 * @Date: 2021-03-27 11:11
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public AdminInfo login(AdminInfo adminInfo) {
        if (StringUtils.isBlank(adminInfo.getAdminName()) || StringUtils.isBlank(adminInfo.getAdminPassword()))
            throw new InternalException("用户名和密码不能为空");
        String pass = SpecialUtil.MD5encode(adminInfo.getAdminPassword());
//        adminInfo.setAdminPassword(pass);
        AdminInfo admin = getAdminInfoLogin(adminInfo);
        System.out.println("login: " + admin);
        if (null == admin) {
            throw new InternalException("用户名或密码错误");
        }else{
            return admin;
        }

    }

    @Override
    public AdminInfo getAdminInfoLogin(AdminInfo adminInfo) {
        QueryWrapper wrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(adminInfo.getAdminName())){
            wrapper.eq("adminName",adminInfo.getAdminName());
        }
        if(StringUtils.isNotBlank(adminInfo.getAdminPassword())){
            wrapper.eq("adminPassword",adminInfo.getAdminPassword());
        }
        return adminMapper.selectOne(wrapper);
    }


    /**
     * 根据条件查询管理员列表（分页）
     *
     * @param conditions
     * @return
     */
    @Override
    public PageUtils getAllAdminInfoList(AdminVO conditions) {
        if(conditions.getPageIndex()==null){
            conditions.setPageIndex(1);
        }
        if(conditions.getPageSize()==null){
            conditions.setPageSize(10);
        }
        //RowBounds rowBounds = new RowBounds(conditionVO.getPageSize()*conditionVO.getPageIndex()+1,conditionVO.getPageSize());
        IPage<AdminInfo> page = new Page<>(conditions.getPageIndex(),conditions.getPageSize());
        QueryWrapper<AdminInfo> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(conditions.getAdminName())){
            wrapper.like("adminName",conditions.getAdminName());
        }
        IPage<AdminInfo> iPage = adminMapper.selectPage(page, wrapper);
        return new PageUtils(iPage);
    }

    /**
     * 根据adminId获取admin信息
     *
     * @param adminId
     * @return
     */
    @Override
    public AdminInfo getAdminInfoById(Integer adminId) {
        QueryWrapper wrapper = new QueryWrapper();
        if(adminId!=null){
            wrapper.eq("adminId",adminId);
        }
        return adminMapper.selectOne(wrapper);
    }

    /**
     * 新增管理员
     *
     * @param adminInfo
     * @return
     */
    @Override
    public boolean save(AdminInfo adminInfo) {
        return adminMapper.insert(adminInfo)>0;
    }

    /**
     * 更新管理员信息
     *
     * @param adminInfo
     * @return
     */
    @Override
    public boolean update(AdminInfo adminInfo) {
        return adminMapper.updateById(adminInfo)>0;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param AdminIds
     * @return
     */
    @Override
    public boolean remove(String AdminIds) {
        if(StringUtils.isNotBlank(AdminIds)){
            String[] idList = AdminIds.split(",");
            int i = adminMapper.deleteBatchIds(Arrays.asList(idList));
            return i>0;
        }
        return false;
    }
}
