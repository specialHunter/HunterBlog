package com.hunter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 博客启动类
 *
 * @author Hunter
 * @since 2024/12/11
 */
@SpringBootApplication
@MapperScan("com.hunter.mapper")
public class HunterBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HunterBlogApplication.class, args);
    }
}
