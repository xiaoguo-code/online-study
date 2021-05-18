package com.gyr.studybusiness.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studybusiness.render.service.UserService;
import com.gyr.studycommon.dao.AdminMapper;
import com.gyr.studycommon.dao.UsersMapper;
import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.SpecialUtil;
import com.gyr.studycommon.vo.AdminVO;
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
public class UserServiceImpl implements UserService {

    @Autowired
    UsersMapper usersMapper;

    @Override
    public UsersInfo login(UsersInfo usersInfo) {
        if (StringUtils.isBlank(usersInfo.getEmail()) || StringUtils.isBlank(usersInfo.getPassword())) {
            throw new InternalException("邮箱和密码不能为空");
        }
        String pass = SpecialUtil.MD5encode(usersInfo.getPassword());
//        adminInfo.setAdminPassword(pass);
        UsersInfo userInfo = getUserInfoLogin(usersInfo);
        System.out.println("login: " + userInfo);
        if (null == userInfo) {
            throw new InternalException("邮箱或密码错误");
        }else{
            return userInfo;
        }

    }

    @Override
    public UsersInfo getUserInfoLogin(UsersInfo usersInfo) {
        QueryWrapper wrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(usersInfo.getEmail())){
            wrapper.eq("email",usersInfo.getEmail());
        }
        if(StringUtils.isNotBlank(usersInfo.getPassword())){
            wrapper.eq("password",usersInfo.getPassword());
        }
        return usersMapper.selectOne(wrapper);
    }

    @Override
    public boolean update(UsersInfo usersInfo) {
        return usersMapper.updateById(usersInfo)>0;
    }

    @Override
    public UsersInfo getUserInfoByEmail(String email) {
        QueryWrapper wrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(email)){
            wrapper.eq("email",email);
        }

        return usersMapper.selectOne(wrapper);
    }

    @Override
    public boolean save(UsersInfo usersInfo) {
        int insert = usersMapper.insert(usersInfo);
        return insert>0;
    }

    @Override
    public UsersInfo getUserInfoById(Integer userId) {
        return usersMapper.selectById(userId);
    }


}
