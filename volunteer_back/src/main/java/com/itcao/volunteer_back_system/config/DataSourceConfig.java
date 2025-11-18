package com.itcao.volunteer_back_system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置类
 */
@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 配置数据源
     * 
     * @return 数据源
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        // 添加参数以支持多结果集
        String enhancedUrl = url;
        if (!enhancedUrl.contains("allowMultiQueries")) {
            enhancedUrl += (enhancedUrl.contains("?") ? "&" : "?") + "allowMultiQueries=true";
        }

        return DataSourceBuilder.create()
                .url(enhancedUrl)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }
}