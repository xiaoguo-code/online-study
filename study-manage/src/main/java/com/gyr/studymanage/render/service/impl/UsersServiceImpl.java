package com.gyr.studymanage.render.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyr.studycommon.dao.UsersMapper;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.CourseInfo;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.vo.UsersVO;
import com.gyr.studymanage.render.service.UsersService;
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
public class UsersServiceImpl implements UsersService {


    @Autowired
    private UsersMapper usersMapper;
    /**
     * 根据条件获取用户信息列表（分页）
     *
     * @param conditions
     * @return
     */
    @Override
    public PageUtils getUserListByCondition(UsersVO conditions) {
        if(conditions.getPageIndex()==null){
            conditions.setPageIndex(1);
        }
        if(conditions.getPageSize()==null){
            conditions.setPageSize(10);
        }
        //RowBounds rowBounds = new RowBounds(conditionVO.getPageSize()*conditionVO.getPageIndex()+1,conditionVO.getPageSize());
        IPage<UsersInfo> page = new Page<>(conditions.getPageIndex(),conditions.getPageSize());
        QueryWrapper<UsersInfo> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(conditions.getUserName())){
            wrapper.like("userName",conditions.getUserName()).or().eq("IDCard",conditions.getIDCard());
        }
        if(StringUtils.isNotBlank(conditions.getIDCard())){
            wrapper.eq("IDCard",conditions.getIDCard());
        }
        IPage<UsersInfo> iPage = usersMapper.selectPage(page, wrapper);
        return new PageUtils(iPage);
    }

    /**
     * 根据userId获取user信息
     *
     * @param userId
     * @return
     */
    @Override
    public UsersInfo getUserInfoById(Integer userId) {
        QueryWrapper wrapper = new QueryWrapper();
        if(userId!=null){
            wrapper.eq("userId",userId);
        }
        return usersMapper.selectOne(wrapper);
    }

    /**
     * 新增管理员
     *
     * @param usersInfo
     * @return
     */
    @Override
    public boolean save(UsersInfo usersInfo) {
        return usersMapper.insert(usersInfo)>0;
    }

    /**
     * 更新管理员信息
     *
     * @param usersInfo
     * @return
     */
    @Override
    public boolean update(UsersInfo usersInfo) {
        return usersMapper.updateById(usersInfo)>0;
    }

    /**
     * 根据id批量删除，ids=“1,2,3,4"
     *
     * @param userIds
     * @return
     */
    @Override
    public boolean remove(String userIds) {
        if(StringUtils.isNotBlank(userIds)){
            String[] idList = userIds.split(",");
            int i = usersMapper.deleteBatchIds(Arrays.asList(idList));
            return i>0;
        }
        return false;
    }
}
