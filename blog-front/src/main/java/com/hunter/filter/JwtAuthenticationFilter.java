package com.hunter.filter;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.LoginUser;
import com.hunter.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 处理请求头中的JWT，并解析其中的用户信息，设置认证信息到SecurityContextHolder中
 *
 * @author Hunter
 * @since 2025/1/19
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中的token
        String authorization = request.getHeader("Authorization"); // 从请求头中获取Authorization字段
        if (authorization != null && authorization.startsWith("Bearer ")) { // 如果Authorization字段存在且以Bearer开头
            String token = authorization.substring(7); // 截取出token
            String userId = null;
            try {
                // 解析获取userid
                userId = JwtUtils.resolveToken(token);
            } catch (JWTDecodeException | TokenExpiredException jwtException) {
                log.error("解析token失败：{}", jwtException.getMessage(), jwtException);
                response.getWriter()
                        .write(
                                ResponseResult.failed(HttpStatus.UNAUTHORIZED.value(),
                                        HttpStatus.UNAUTHORIZED.getReasonPhrase()).toJson()
                        );
                return;
            }
            // 从redis中获取用户信息
            LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get("login:user:id:" + userId);
            // 获取不到用户信息
            if (loginUser == null) {
                log.error("redis中没有用户信息");
                response.getWriter()
                        .write(
                                ResponseResult.failed(HttpStatus.UNAUTHORIZED.value(),
                                        HttpStatus.UNAUTHORIZED.getReasonPhrase()).toJson()
                        );
                return;
            }
            // 设置认证信息到SecurityContextHolder中
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            // 设置与当前http请求相关的详细信息
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // 放行请求到下一个过滤器
        filterChain.doFilter(request, response);
    }
}
