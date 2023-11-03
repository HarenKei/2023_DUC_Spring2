package com.example.ch05.config;


import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    // 스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드
    // 즉, 인가 인증을 모두 적용하지 않는다.
    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"),new AntPathRequestMatcher("/static/**/**")));
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> { // 인증, 인가 설정 : 특정 경로에 대한 액세스 설정
                    request.requestMatchers(antMatcher("/login")).permitAll();
                    request.requestMatchers(antMatcher("/signup")).permitAll();
                    request.requestMatchers(antMatcher("/user")).permitAll();

                    request.anyRequest().authenticated();
                });


        // 로그인 설정
        http.formLogin(login -> login	// form 방식 로그인 사용
                .loginPage("/login") // 로그인 페이지 경로 설정
                .defaultSuccessUrl("/articles", true)	// 성공 시 이동할 경로 설정
                .permitAll()	//  이동이 막히면 안되므로 허용
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutSuccessUrl("/login") // 로그아웃시 이동할 경로 설정
                .invalidateHttpSession(true));// 로그아웃 이후에 세션 전체를 삭제할 지 여부 설정

        return http.build();
    }

    // 패스워드 인코더 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}