package com.hunter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis配置类
 *
 * @author Hunter
 * @since 2025/1/14
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory); // 设置连接工厂

        // 使用StringRedisSerializer.UTF_8作为key和hash key的序列化器
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        // 使用GenericJackson2JsonRedisSerializer作为value和hash value的序列化器。
        // 被序列化的对象，getter方法也会被读取形成property，成为被序列化的一部分
        ObjectMapper objectMapper = new ObjectMapper();
        // 注册JavaTimeModule，支持LocalDateTime等时间类型
        objectMapper.registerModule(new JavaTimeModule());
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 对配置进行初始化
        template.afterPropertiesSet();
        return template;
    }
}
