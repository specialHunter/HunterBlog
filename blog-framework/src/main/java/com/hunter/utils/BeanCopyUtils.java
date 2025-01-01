package com.hunter.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * bean拷贝 工具类
 *
 * @author Hunter
 * @since 2025/1/1
 */
public class BeanCopyUtils {
    /**
     * bean拷贝。目标bean的字段名 和 类型 都需要和源bean一致，否则无法拷贝。
     * @param source 源bean
     * @param clazz 目标bean对应的Class
     * @return 目标bean
     * @param <V> 目标bean对应的具体类
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        try {
            // 创建目标对象
            V target = clazz.getDeclaredConstructor().newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, target);
            // 返回结果
            return target;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * beanList拷贝
     * @param sourceList 源bean List
     * @param clazz 目标bean对应的Class
     * @return 目标bean List
     * @param <V> 目标bean对应的具体类
     */
    public static <V> List<V> copyBeanList(List<?> sourceList, Class<V> clazz) {
        return sourceList.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }

}
