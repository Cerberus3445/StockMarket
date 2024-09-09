package com.cerberus.demotradingweb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/css/**")
                        .permitAll()
                        .requestMatchers("/stock/trading")
                        .permitAll()
                        .requestMatchers("/stockmarket/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .hasAnyRole("ADMIN", "USER"))
                .formLogin(login -> login.
                        loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/stockmarket/trading", true)
                        .failureUrl("/auth/login?error"))
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/auth/login"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
