package com.hunter.handler.security;

import com.hunter.constants.RedisConstants;
import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.LoginUser;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 前台用户注销成功处理器
 *
 * @author Hunter
 * @since 2025/1/21
 */
@Component
@Slf4j
public class FrontLogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        // logout请求需要携带正确的token，才能执行登出操作
        if (authentication == null) {
            writer.write(
                    ResponseResult.failed(HttpStatus.UNAUTHORIZED.value(),
                                    HttpStatus.UNAUTHORIZED.getReasonPhrase())
                            .toJson()
            );
        } else {
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            // 删除redis中存储的用户信息
            redisTemplate.delete(RedisConstants.FRONT_LOGIN_USER_ID + loginUser.getUser().getId());
            writer.write(
                    ResponseResult.success().toJson()
            );
        }
    }
}
