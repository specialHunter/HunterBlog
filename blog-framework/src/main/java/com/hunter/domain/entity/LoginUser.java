package com.hunter.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

/**
 * 针对登录用户的实体类，不提供过多属性
 *
 * @author Hunter
 * @since 2025/1/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
// 由于redis使用GenericJackson2JsonRedisSerializer作为value和hash value的序列化器。
// 被序列化的对象，getter方法也会被读取形成property，成为被序列化的一部分。反序列化时，需要忽略这些由方法产生的字段。
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知属性
public class LoginUser implements UserDetails {
    @Serial
    private static final long serialVersionUID = 9026640040252480961L;

    /**
     * 将数据库中数据对应的User实体类注入到LoginUser中
     */
    private User user;

    // todo: 权限信息的封装
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
