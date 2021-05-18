package com.gyr.studybusiness.render.controller;

import com.gyr.studybusiness.render.service.CategoryService;
import com.gyr.studybusiness.render.service.UserService;
import com.gyr.studycommon.entity.CategoryInfo;
import com.gyr.studycommon.entity.UsersInfo;
import com.gyr.studycommon.entity.WebConst;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.util.RedisUtil;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 登录
 * @Author: guoyr
 * @Date: 2021-05-09 23:16
 */

@Controller
@Slf4j
@RequestMapping("/business")
public class LoginController {


    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private RedisUtil redis;
    private static Integer error_count = 0;
    @GetMapping("/login")
    public ModelAndView toLogin(Model model){
        List<CategoryInfo> categoryListOne = categoryService.getParentCategory(0);
        List<CategoryInfo> categoryListTwo = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(categoryListOne)){
            CategoryInfo obj1 = categoryListOne.get(0);
            model.addAttribute("parentId",obj1.getCategoryId());
//            conditionVO.setParentId(obj1.getCategoryId());
            categoryListTwo = categoryService.getParentCategory(obj1.getCategoryId());
        }
        model.addAttribute("one",categoryListOne);
        model.addAttribute("two",categoryListTwo);
        return new ModelAndView("login","indexModel",model);
    }


    @PostMapping("/login")
    @ResponseBody
    public R login(UsersInfo usersInfo,
                   HttpServletRequest request,
                   HttpServletResponse response){
        Integer login_status = (Integer) redis.get("login_status_user");
        System.out.println(login_status);
        try {
            if(login_status!=null&&login_status==2){
                return R.error("您输入错误次数过多，请5分钟后再试！");
            }
            // 调用Service登录方法
            UsersInfo userInfo = userService.login(usersInfo);
            // 设置登录用户session
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_USER_KEY, userInfo);
            userInfo.setStatus(1);
            userService.update(userInfo);
            // 判断是否勾选记住我
//            if (StringUtils.isNotBlank(remember_me)) {
//                CookieUtil.setCookie(response, WebConst.USER_IN_COOKIE, String.valueOf(user.getUid()));
//            }
//            Log log = Commons.newLog(LogEnum.LOGIN.getAction(),user.getUsername()+" 用户",user.getUid(),request);
//            logService.insertLog(log);

        } catch (InternalException e) {
            if(error_count>2){
                redis.set("login_status_user",2,60*2);
                error_count = 0;
            }
            error_count++;
            System.out.println(error_count);
            if(login_status!=null&&login_status==2){
                return R.error("您输入错误次数过多，请5分钟后再试！");
            }
            String msg = e.getMessage();
            return R.error(msg);
        }

        return R.ok();
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 移除session
        UsersInfo userInfo = (UsersInfo) session.getAttribute(WebConst.LOGIN_SESSION_USER_KEY);
        session.removeAttribute(WebConst.LOGIN_SESSION_USER_KEY);
        userInfo.setStatus(0);
        userService.update(userInfo);
        // 移除cookie
//        CookieUtil.removeCookie(request,response,WebConst.USER_IN_COOKIE);
        // 跳转到登录页面
        try {
            response.sendRedirect("/business/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/register")
    @ResponseBody
    public R register(UsersInfo usersInfo){
        usersInfo.setStatus(0);
        UsersInfo user = userService.getUserInfoByEmail(usersInfo.getEmail());
        if(user!=null){
            return  R.error("用户已存在!");
        }
        boolean save = userService.save(usersInfo);
        return  R.ok();
    }


}
