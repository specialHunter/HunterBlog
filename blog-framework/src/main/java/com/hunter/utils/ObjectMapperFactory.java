package com.hunter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Jackson序列化相关，支持LocalDateTime等时间类型的ObjectMapper工厂类
 * 不选择注册bean，否则会影响到其他使用ObjectMapper的地方
 * @author Hunter
 * @since 2025/2/6
 */
public class ObjectMapperFactory {

    private static ObjectMapper objectMapper;

    /**
     * 获取支持LocalDateTime等时间类型的ObjectMapper实例
     * @return ObjectMapper实例
     */
    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            // 注册JavaTimeModule，支持LocalDateTime等时间类型
            objectMapper.registerModule(new JavaTimeModule());
        }
        return objectMapper;
    }
}
