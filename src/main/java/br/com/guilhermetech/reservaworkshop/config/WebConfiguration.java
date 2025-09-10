package br.com.guilhermetech.reservaworkshop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebConfiguration {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration()
                .setAllowedOriginPatterns(singletonList("*"))
                .applyPermitDefaultValues();
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Origin", "Content-Type", "Accept"));
        configuration.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "DELETE", "X-FORWARDED-FOR"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        var cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", configuration);
        return cors;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(this.corsConfigurationSource()))
                .sessionManagement((session) -> session.sessionCreationPolicy(STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(POST, "/**").permitAll()
                        .requestMatchers(GET, "/**").permitAll()
                        .requestMatchers(PUT, "/**").permitAll()
                        .requestMatchers(DELETE, "/**").permitAll()
                        .anyRequest().authenticated());
        // security.addFilterBefore(this.webFilter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
