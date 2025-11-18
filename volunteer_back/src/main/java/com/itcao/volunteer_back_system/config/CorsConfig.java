package com.itcao.volunteer_back_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // 允许跨域的头部信息
        config.addAllowedHeader("*");
        // 允许跨域的方法
        config.addAllowedMethod("*");
        // 允许跨域的源
        config.addAllowedOriginPattern("*");
        // 是否允许携带cookie跨域
        config.setAllowCredentials(true);
        // 设置预检请求的有效期，单位为秒
        config.setMaxAge(3600L);
        // 对所有接口都有效
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}