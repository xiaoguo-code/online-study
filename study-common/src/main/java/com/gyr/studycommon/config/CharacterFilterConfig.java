package com.gyr.studycommon.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @desc: 字符编码设置
 * @Author: guoyr
 * @Date: 2021-03-27 15:18
 */
@Configuration
public class CharacterFilterConfig {
    /**
     * 若使用该配置，配置文件中的
     * spring：
     *   http:
     *     encoding:
     *       charset: utf-8
     *       enabled: true
     *       force: true
     *   需要将enabled改为false
     */
//    @Bean
//    public FilterRegistrationBean filterRegistrationBean (){
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setForceEncoding(true);
//        characterEncodingFilter.setEncoding("UTF-8");
//        registrationBean.setFilter(characterEncodingFilter);
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }
}
