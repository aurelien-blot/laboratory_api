package com.castruche.laboratory_api.main_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig() {

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated())
        ;
        return http.build();*/
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Libre accès à tout /api/test
                        .requestMatchers("/api/test").permitAll()
                        // Libre accès à /api/my-world/login
                        .requestMatchers("/api/my-world/login").permitAll()
                        .requestMatchers("/api/my-world/test").permitAll()
                        // Tout le reste de /api/my-world/** nécessite authentification
                        .requestMatchers("/api/my-world/**").authenticated()
                        // Tout le reste de /api/** est public
                        .requestMatchers("/api/**").permitAll()
                        // Par défaut : bloqué
                        .anyRequest().denyAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
