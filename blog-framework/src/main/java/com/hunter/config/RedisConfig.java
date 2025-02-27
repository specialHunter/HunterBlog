package com.hunter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunter.utils.ObjectMapperFactory;
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

    @Bean("customRedisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 使用StringRedisSerializer.UTF_8作为key和hash key的序列化器
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        // 使用GenericJackson2JsonRedisSerializer作为value和hash value的序列化器。
        // 被序列化的对象，getter方法也会被读取形成property，成为被序列化的一部分
        ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer(objectMapper);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        // 对配置进行初始化
        template.afterPropertiesSet();
        return template;
    }
}
