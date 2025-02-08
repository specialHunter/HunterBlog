package com.hunter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动生成OpenAPI文档的配置类
 *
 * @author Hunter
 * @since 2025/2/8
 */
@Configuration
public class SpringdocOpenapiConfig {

    @Bean
    public OpenAPI customOpenapi() {
        return new OpenAPI()
                .info(new Info().title("特叔博客API文档")
                        .version("v1.0"));
    }
}
