package com.example.demo.config;

import com.example.demo.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类：注册拦截器，配置拦截/放行规则
 */
@Configuration // 标记为配置类，Spring自动加载
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注册拦截器并配置规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/**") // 拦截所有/api/**接口
                .excludePathPatterns("/api/user/login"); // 基础放行（兜底）
    }
}