//package com.sklookiesmu.wisefee.common.hash;
//
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class HashSecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .httpBasic().disable()
//                .cors().disable()
//                .cors().and()
//                .authorizeRequests()
//                .antMatchers("/api/v1/member").permitAll()
//                .and()
//                .build();
//    }
//
//}
