package com.eventos.calendario.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                // Endpoints públicos - Eventos
                                .requestMatchers(HttpMethod.GET, "/api/eventos/publico").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/eventos/publico/**").permitAll()
                                
                                // Endpoints públicos - Autenticação
                                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/validate").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/api/auth/**").permitAll()
                                
                                // Endpoints públicos - Health Check
                                .requestMatchers(HttpMethod.GET, "/api/health").permitAll()
                                .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                                
                                // Endpoints protegidos - Eventos (Admin e User)
                                .requestMatchers(HttpMethod.GET, "/api/eventos").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/api/eventos").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/api/eventos/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/eventos/buscar").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/eventos/data/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.GET, "/api/eventos/**").hasAnyRole("ADMIN", "USER")
                                
                                // Endpoints protegidos - Usuários (apenas Admin)
                                .requestMatchers(HttpMethod.GET, "/api/usuarios").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
                                
                                // Endpoints protegidos - Perfil do usuário
                                .requestMatchers(HttpMethod.GET, "/api/perfil").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.PUT, "/api/perfil").hasAnyRole("ADMIN", "USER")
                                
                                // Qualquer outra requisição precisa estar autenticada
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}