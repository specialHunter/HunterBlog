package com.hunter.utils;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Jackson序列化相关，支持LocalDateTime等时间类型的ObjectMapper工厂类
 * 不选择注册bean，否则会影响到其他使用ObjectMapper的地方
 *
 * @author Hunter
 * @since 2025/2/6
 */
public class ObjectMapperFactory {

    private static ObjectMapper objectMapper;

    /**
     * 获取支持LocalDateTime等时间类型的ObjectMapper实例
     *
     * @return ObjectMapper实例
     */
    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            // 注册JavaTimeModule，支持LocalDateTime等时间类型
            objectMapper.registerModule(new JavaTimeModule());

            // 类型验证器，在反序列化时验证类型信息的安全性
            BasicPolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                    .allowIfBaseType(Object.class)
                    .build();
            // 启用默认类型信息，使得序列化时包含类型信息，反序列化时根据类型信息进行类型判断。否则，反序列化复杂的泛型实体类型时会报错。
            objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL,
                    JsonTypeInfo.As.PROPERTY);
        }
        return objectMapper;
    }
}
