package com.goodlab.config;


import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().anyRequest()
                .authenticated()
                .and()
                .cors()
                .and()
                .formLogin()
                .permitAll()
                .and()
                /*设置会话创建策略为无状态。*/
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //用于禁用跨站请求伪造（CSRF）保护。
                .csrf().disable();
        return http.build();
    }

    public void writeResp(Object content, HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(JSON.toJSONString(content));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
