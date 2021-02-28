package com.gyr.onlinestudy.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SystemWebMvcConfigure implements WebMvcConfigurer {
    @Autowired
    CenterInterceptor centerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(centerInterceptor).addPathPatterns("/center/**");
    }

}
