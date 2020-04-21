package com.root.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig {}
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().disable()
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/api/**")
//                .permitAll();
//    }
//}
