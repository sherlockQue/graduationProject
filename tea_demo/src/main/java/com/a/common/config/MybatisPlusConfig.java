package com.a.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * 开启分页
 * @author admin
 *
 */
@Configuration
public class MybatisPlusConfig {
	
	//注解分页插件
    @Bean
    PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
    
}
