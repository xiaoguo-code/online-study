package com.gyr.studymanage.render.controller;

import com.gyr.studycommon.entity.AdminInfo;
import com.gyr.studycommon.entity.WebConst;
import com.gyr.studycommon.util.R;
import com.gyr.studycommon.util.RedisUtil;
import com.gyr.studymanage.render.service.AdminService;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @desc: 登录
 * @Author: guoyr
 * @Date: 2021-05-09 23:16
 */

@Controller
@Slf4j
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    AdminService adminService;
    @Autowired
    private RedisUtil redis;
    private static Integer error_count = 0;
    @GetMapping("/login")
    public String toLogin(){
        return "login";
    }


    @PostMapping("/login")
    @ResponseBody
    public R login(AdminInfo adminInfo,
                   HttpServletRequest request,
                   HttpServletResponse response){
        Integer login_status = (Integer) redis.get("login_status");
        System.out.println(login_status);
        try {
            if(login_status!=null&&login_status==2){
                return R.error("您输入错误次数过多，请5分钟后再试！");
            }
            // 调用Service登录方法
            AdminInfo admin = adminService.login(adminInfo);
            // 设置登录用户session
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, admin);
            admin.setStatus(1);
            adminService.update(admin);
            // 判断是否勾选记住我
//            if (StringUtils.isNotBlank(remember_me)) {
//                CookieUtil.setCookie(response, WebConst.USER_IN_COOKIE, String.valueOf(user.getUid()));
//            }
//            Log log = Commons.newLog(LogEnum.LOGIN.getAction(),user.getUsername()+" 用户",user.getUid(),request);
//            logService.insertLog(log);

        } catch (InternalException e) {
            if(error_count>2){
                redis.set("login_status",2,60*2);
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
        AdminInfo adminInfo = (AdminInfo) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        adminInfo.setStatus(0);
        adminService.update(adminInfo);
        // 移除cookie
//        CookieUtil.removeCookie(request,response,WebConst.USER_IN_COOKIE);
        // 跳转到登录页面
        try {
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
