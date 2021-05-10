package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.util.DateUtils;
import com.gyr.studycommon.util.PageUtils;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.vo.AdminVO;
import com.gyr.studycommon.vo.UsersVO;
import com.gyr.studymanage.render.service.AdminService;
import com.gyr.studymanage.render.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @desc: 学习路线管理
 * @Author: guoyr
 * @Date: 2021-05-03 18:34
 */

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService   adminService;


    /**
     *  根据学科类别获取课程对应的学习路线列表
     */
    @RequestMapping("/listAdmin")
    public ModelAndView listChildByParentIdNotAjax(@RequestParam(value = "async",required = false) boolean async,
                                                   AdminVO conditions,
                                                   Model model) {
        log.info("1、查询会员列表!");
        PageUtils page = null;
       try {
            page = adminService.getAllAdminInfoList(conditions);
           model.addAttribute("page",page);
           model.addAttribute("adminList",page.getList());
       } catch (Exception e) {
            log.error("查询会员列表异常", e);
        }
        return new ModelAndView(!async?"user-admin/admins":"user-admin/admins::#tableBox",
                "adminsModel",model);
    }

    @RequestMapping("/addUserPage")
    public ModelAndView addUserPage() {

//        UsersInfo userInfo = usersService.getUserInfoById(userId);
//        model.addAttribute("userInfo",userInfo);
        return new ModelAndView("user-admin/add_admin");
    }


    @RequestMapping("/editUserPage")
    public ModelAndView editUserPage(Integer userId,Model model) {

        AdminInfo adminInfo = adminService.getAdminInfoById(userId);
        model.addAttribute("adminInfo",adminInfo);
        return new ModelAndView("user-admin/edit_admin");
    }

    @PostMapping("/update")
    @ResponseBody
//    @RequiresPermissions("generator:category:delete")
    public R update(AdminInfo adminInfo) {
        log.info("更新管理员="+adminInfo);
        try {
            adminInfo.setUpdateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
            boolean update = adminService.update(adminInfo);
        } catch (Exception e) {
            log.error("更新异常：", e);
            return R.error();
        }
        return R.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ResponseBody
//    @RequiresPermissions("generator:category:delete")
    public R save(AdminInfo adminInfo) {
        log.info("保存会员="+adminInfo);
        try {
            adminInfo.setCreateTime(DateUtils.format(new Date(),DateUtils.DATE_TIME_PATTERN));
            adminInfo.setStatus(0);
            boolean save = adminService.save(adminInfo);
        } catch (Exception e) {
            log.error("保存异常：", e);
            return R.error();
        }
        return R.ok();
    }


    /**
     * 删除
     */
    @PostMapping("/delete")
    @ResponseBody
//    @RequiresPermissions("generator:category:delete")
    public R delete(@RequestParam String adminIds) {
        log.info("删除会员userIds：{}" , adminIds);
        try {
            //Map<String, String> stringObjectMap = GsonUtils.toMaps(categoryIds);
            boolean flag = adminService.remove(adminIds);
            if(flag){
                log.info("删除成功！-{}", flag);
            }else{
                log.error("删除失败");
                return R.error();
            }
        } catch (Exception e) {
            log.error("删除异常：", e);
            return R.error();
        }
        return R.ok();
    }

}
