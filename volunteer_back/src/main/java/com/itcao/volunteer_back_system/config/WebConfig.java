package com.itcao.volunteer_back_system.config;

import com.itcao.volunteer_back_system.filter.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionFilter sessionFilter;

    /**
     * 注册Session过滤器
     * 
     * @return 过滤器注册Bean
     */
    @Bean
    public FilterRegistrationBean<SessionFilter> sessionFilterRegistration() {
        FilterRegistrationBean<SessionFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/*");
        registration.setName("sessionFilter");
        registration.setOrder(1);
        return registration;
    }
}