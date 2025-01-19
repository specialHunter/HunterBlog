package com.hunter.domain.entity;

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
        return user.getUserName();
    }
}
