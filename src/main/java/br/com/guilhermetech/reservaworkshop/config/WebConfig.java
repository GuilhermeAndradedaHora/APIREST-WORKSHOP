package br.com.guilhermetech.reservaworkshop.config;

import br.com.guilhermetech.reservaworkshop.config.security.CustomUserDetailService;
import br.com.guilhermetech.reservaworkshop.config.security.JwtFilter;
import br.com.guilhermetech.reservaworkshop.config.security.JwtUtil;
import br.com.guilhermetech.reservaworkshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebConfig {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

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
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers(GET, "/workshop/**").permitAll()
                        .anyRequest().authenticated());
        security.addFilterBefore(this.jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }

    @Bean
    public AuthenticationManager authnManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService(this.userRepository);
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(this.jwtUtil, this.userDetailsService());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
