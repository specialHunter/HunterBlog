package com.hunter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 博客后台管理系统启动类
 *
 * @author Hunter
 * @since 2025/2/10
 */
@SpringBootApplication
// 扫描 mapper 包，将 mapper 注入到 Spring 容器中
@MapperScan("com.hunter.mapper")
public class BlogAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
