package com.hunter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 *
 * @author Hunter
 * @since 2025/1/15
 */
public class JwtUtils {
    /**
     * JWT密钥
     * todo: 解耦，将 硬编码的密钥 从代码中移除，移到配置文件中
     */
    private static final String SECRET_KEY = "hunter";

    /**
     * 生成JWT，默认过期时间24小时
     *
     * @param userId token中要存放的数据，userId
     * @return JWT
     */
    public static String createJwt(String userId) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // 对称加密算法
        Calendar calendar = Calendar.getInstance(); // 获取当前时间
        Date now = calendar.getTime(); // 获取当前时间
        // calendar.add(Calendar.HOUR, 24); // 设置过期时间为24小时
        calendar.add(Calendar.HOUR, 24); // 设置过期时间为30秒
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString()) // 将UUID设置为JWT ID
                .withSubject(userId) // 设置主题，可以是json数据
                .withIssuedAt(now) // 设置签发时间
                .withExpiresAt(calendar.getTime()) // 设置过期时间
                .sign(algorithm); // 签名并生成JWT
    }

    /**
     * 解析JWT，获取userId
     *
     * @param token token
     * @return userId
     */
    public static String resolveToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY); // 对称加密算法
        DecodedJWT verify = JWT.require(algorithm).build().verify(token); // 验证token
        if (new Date().after(verify.getExpiresAt())) { // token过期
            return null;
        } else {
            return verify.getSubject(); // 获取userId
        }
    }
}
