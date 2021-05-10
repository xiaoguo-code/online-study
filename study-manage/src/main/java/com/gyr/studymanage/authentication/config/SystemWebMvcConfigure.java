package com.gyr.studymanage.authentication.config;

import com.gyr.studymanage.authentication.interceptor.CenterInterceptor;
import com.gyr.studymanage.authentication.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SystemWebMvcConfigure implements WebMvcConfigurer {
    @Autowired
    CenterInterceptor centerInterceptor;

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(centerInterceptor).addPathPatterns("/center/**");

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/**");

    }

}
