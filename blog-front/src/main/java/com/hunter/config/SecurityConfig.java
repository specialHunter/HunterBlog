package com.hunter.config;

import com.hunter.domain.ResponseResult;
import com.hunter.domain.entity.LoginUser;
import com.hunter.domain.vo.LoginUserVo;
import com.hunter.domain.vo.UserInfoVo;
import com.hunter.filter.JsonUsernamePasswordAuthenticationFilter;
import com.hunter.filter.JwtAuthenticationFilter;
import com.hunter.handler.security.LogoutSuccessHandlerImpl;
import com.hunter.utils.BeanCopyUtils;
import com.hunter.utils.JwtUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author Hunter
 * @since 2025/1/15
 */
@Configuration
@Slf4j
public class SecurityConfig {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource(name = "customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) // 关闭CSRF校验
                .cors(configurer -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.addAllowedOriginPattern("*"); // 允许所有地址跨域访问
                    corsConfig.setAllowCredentials(true); // 允许携带cookie
                    corsConfig.addAllowedHeader("*"); // 允许所有请求头
                    corsConfig.addAllowedMethod("*"); // 允许所有请求方法
                    corsConfig.addExposedHeader("*"); // 允许所有响应头
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 基于URL的Cors配置源
                    source.registerCorsConfiguration("/**", corsConfig); // 注册Cors配置，对所有接口都生效
                    configurer.configurationSource(source); // 将Cors配置源注入到SpringSecurity
                })
                // 不使用session
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").anonymous()// "/login" 允许匿名访问（没有经过身份验证），已登录用户无法访问
                            .requestMatchers(
                                    "/logout", "/comment", "/user/userInfo", "/upload"
                            ).authenticated() // 需要认证才能访问的请求
                            .anyRequest().permitAll(); // 其他所有请求都允许访问
                })
                .formLogin(configurer -> {
                    configurer.loginProcessingUrl("/login") // 登录请求处理url
                            .successHandler(this::onAuthenticationSuccess) // 登录成功处理器
                            .failureHandler(this::onAuthenticationFailure); // 登录失败处理器
                })
                .logout(configurer -> {
                    configurer.logoutUrl("/logout")
                            .logoutSuccessHandler(logoutSuccessHandler); // 注销成功处理器
                })
                .addFilterBefore(jsonUsernamePasswordAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class)
                // 在注销过滤器之前添加JWT过滤器，否则注销无法验证token
                .addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
                .userDetailsService(userDetailsService) // 自定义用户验证服务
                .exceptionHandling(configurer -> {
                            configurer.authenticationEntryPoint(this::onAuthenticationFailure) // 认证失败处理器
                                    .accessDeniedHandler(this::onAccessDenied); // 已登录，但没有权限访问时的处理器
                        }
                )
                .build();
    }

    private void onAccessDenied(HttpServletRequest request, HttpServletResponse response,
                                AccessDeniedException accessDeniedException) throws IOException {
        log.error("权限不足：{}", accessDeniedException.getMessage(), accessDeniedException);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(
                ResponseResult.failed(response.getStatus(), accessDeniedException.getMessage())
                        .toJson());
    }

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
        JsonUsernamePasswordAuthenticationFilter authenticationFilter =
                new JsonUsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager());
        // 参考 https://www.duidaima.com/Group/Topic/JAVA/10494 , todo: 梳理SpringSecurity处理json请求的流程
        authenticationFilter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        authenticationFilter.setFilterProcessesUrl("/login");
        authenticationFilter.setAuthenticationSuccessHandler(this::onAuthenticationSuccess);
        authenticationFilter.setAuthenticationFailureHandler(this::onAuthenticationFailure);
        return authenticationFilter;
    }

    private AuthenticationManager authenticationManager() {
        // 认证提供器
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 将BCryptPasswordEncoder直接注册为Bean，Security会自动处理密码对比
        return new BCryptPasswordEncoder();
    }

    private void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception) throws IOException {
        log.error("认证失败：{}", exception.getMessage(), exception);
        String message = null;
        if (exception instanceof BadCredentialsException badCredentialsException) {
            message = badCredentialsException.getMessage();
        } else if (exception instanceof InsufficientAuthenticationException insufficientAuthenticationException) {
            message = "认证失败，请登录后操作";
        } else {
            message = exception.getMessage();
        }
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(
                ResponseResult.failed(HttpStatus.UNAUTHORIZED.value(), message)
                        .toJson());
    }

    // 登录成功处理器
    private void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String token = JwtUtils.createJwt(loginUser.getUser().getId().toString()); // 根据用户ID生成token
        // 将用户信息存入redis，todo:优化硬编码login:user:id:
        redisTemplate.opsForValue().set("login:user:id:" + loginUser.getUser().getId(), loginUser);
        // 将用户信息封装到LoginUserVo中
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        LoginUserVo loginUserVo = new LoginUserVo(token, userInfoVo);
        writer.write(
                ResponseResult.success(loginUserVo)
                        .toJson()
        );
    }
}
