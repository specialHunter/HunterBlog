package com.hunter.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Hunter
 * @since 2025/1/19
 */
public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())
                || MediaType.APPLICATION_JSON_UTF8_VALUE.equalsIgnoreCase(request.getContentType())) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                // 通过ObjectMapper读取request中的I/O流，将JSON映射到Map上
                Map<String, String> authenticationBean = mapper.readValue(is, new TypeReference<>() {
                });
                authRequest = new UsernamePasswordAuthenticationToken(authenticationBean.get("username"),
                        authenticationBean.get("password"));
            } catch (IOException e) {
                authRequest = new UsernamePasswordAuthenticationToken("", "");
                throw new AuthenticationServiceException("Failed to read JSON request body", e);
            } finally {
                setDetails(request, authRequest);
            }
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
