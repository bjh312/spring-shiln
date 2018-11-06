package com.shiln.user.interceptor;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p> 类描述：
 * <p> 创建人: baojunhu
 * <p> 创建时间: 2018/8/28 18:15
 * <p> 版权申明：Huobi All Rights Reserved
 */
@SpringBootConfiguration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        // registry.addInterceptor(new InterceptorConfig()).addPathPatterns("api/path/**").excludePathPatterns("api/path/login");
        registry.addInterceptor(new OneInterceptor()).addPathPatterns("user/**");
    }
}
