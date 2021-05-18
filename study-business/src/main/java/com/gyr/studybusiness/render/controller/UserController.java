package com.gyr.studybusiness.render.controller;

import com.gyr.studybusiness.render.service.UserService;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.entity.WebConst;
import com.gyr.studycommon.util.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.WeakHashMap;

/**
 * @desc: 用户中心控制器
 * @Author: guoyr
 * @Date: 2021-05-17 22:52
 */

@Controller
@Slf4j
@RequestMapping("/business/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/showUserInfo")
    public ModelAndView showUserInfo(HttpSession session, Model model){

        UsersInfo userInfo = (UsersInfo) session.getAttribute(WebConst.LOGIN_SESSION_USER_KEY);
        if (userInfo!=null){
            model.addAttribute("userInfo",userInfo);
            return new ModelAndView("userInfo","userInfoModel",model);
        }else{
            return new ModelAndView("redirect:/business/login");
        }

    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public R userInfo(UsersInfo userInfo,HttpSession session){
        boolean flag = userService.update(userInfo);
        UsersInfo newObj = userService.getUserInfoById(userInfo.getUserId());
        session.setAttribute(WebConst.LOGIN_SESSION_USER_KEY,newObj);

        if (flag){
            return R.ok();
        }else{
            return R.error("信息完善保存失败！");
        }
    }



}
