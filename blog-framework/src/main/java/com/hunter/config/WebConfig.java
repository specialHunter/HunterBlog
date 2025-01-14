package com.hunter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC配置类
 *
 * @author Hunter
 * @since 2025/1/1
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 设置允许访问的路径，/** 表示所有路径都允许被跨域访问
                .allowedOriginPatterns("*") // 设置允许跨域请求的域名，* 接受来自任何域名的请求
                .allowCredentials(true) // 允许跨域请求携带用户凭证（如 cookies）
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name(),
                        HttpMethod.PUT.name()) // 设置允许的请求方式
                .allowedHeaders("*") // 设置允许的header属性，* 表示允许所有请求头的访问
                .maxAge(3600); // 跨域允许时间，单位s，在这段时间内，浏览器可以缓存该请求的结果，避免频繁发送预检请求。
    }
}
