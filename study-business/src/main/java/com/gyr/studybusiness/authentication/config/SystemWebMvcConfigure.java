package com.gyr.studybusiness.authentication.config;


import com.gyr.studybusiness.authentication.interceptor.CenterInterceptor;
import com.gyr.studybusiness.authentication.interceptor.LoginInterceptor;
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
