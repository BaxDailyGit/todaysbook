package com.example.todaysbook.config;

import com.example.todaysbook.domain.entity.Role;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth ->              // 인증, 인가 설정
                        auth.requestMatchers(
                                        "/", "/login", "/alan/**","/gemini/**", "/user/**", "/category/**",
                                        "/checkEmailAvailability", "/checkNicknameAvailability", "checkPasswordAvailability",
                                        "/book/**", "/signup","/favorite_book/**","/cart/**","/bookmark/**","/review/**","/payment/stock/**","/payment/info").permitAll()
                                .requestMatchers(
                                        "/mypage/**",
                                        "/alan/**",
                                        "/recommend/**",
                                        "/payment/card" ,"/payment/success", "/payment/fail",
                                        "/payment/confirm", "/payment/card/order"
                                ).hasAnyRole(Role.ROLE_BRONZE.getName(), Role.ROLE_SILVER.getName()
                                        , Role.ROLE_GOLD.getName(), Role.ROLE_DIAMOND.getName(), Role.ROLE_ADMIN.getName())
                                .requestMatchers("/admin/**").hasAnyRole(Role.ROLE_ADMIN.getName())
                                .anyRequest().authenticated())
                .formLogin(auth -> auth.loginPage("/login")
                        .defaultSuccessUrl("/",true))
                .logout(auth -> auth.logoutSuccessUrl("/")
                        .invalidateHttpSession(true))
                .csrf(auth -> auth.disable())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
