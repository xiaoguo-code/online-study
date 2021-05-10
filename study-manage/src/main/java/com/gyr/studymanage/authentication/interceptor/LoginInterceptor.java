package com.gyr.studymanage.authentication.interceptor;

import com.gyr.studycommon.entity.WebConst;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录跳转
        System.out.println("===========LoginInterceptor  preHandle====");
        // 请求URL不包含域名
        String uri = request.getRequestURI();

        HttpSession session = request.getSession();
        Object user = session.getAttribute(WebConst.LOGIN_SESSION_KEY);

        if(null == user && uri.startsWith("/admin")
                && !uri.startsWith("/admin/login") && !uri.startsWith("/static/css")
                && !uri.startsWith("/static/img") && !uri.startsWith("/static/js")
                && !uri.startsWith("/static/course") && !uri.startsWith("/static/studyLine")
                && !uri.startsWith("/static/tag") && !uri.startsWith("/static/user-admin")
                && !uri.startsWith("/static/category") && !uri.startsWith("/static/component")
                && !uri.startsWith("/static/article")){
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
