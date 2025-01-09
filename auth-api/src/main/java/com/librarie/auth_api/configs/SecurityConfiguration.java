package com.librarie.auth_api.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Routes publiques (inscription, login, etc.)
                        .requestMatchers("/auth/signup", "/auth/login", "/auth/confirm-email", "/auth/forgot-password", "/auth/reset-password")
                        .permitAll()

                        // Routes admin
                        .requestMatchers(HttpMethod.GET, "/api/admin/**").hasAuthority("admin:read")
                        .requestMatchers(HttpMethod.POST, "/api/admin/**").hasAuthority("admin:create")
                        .requestMatchers(HttpMethod.PUT, "/api/admin/**").hasAuthority("admin:update")
                        .requestMatchers(HttpMethod.DELETE, "/api/admin/**").hasAuthority("admin:delete")

                        // Routes de gestion
                        .requestMatchers(HttpMethod.GET, "/api/management/**").hasAnyAuthority("management:read", "admin:read")
                        .requestMatchers(HttpMethod.POST, "/api/management/**").hasAnyAuthority("management:create", "admin:create")
                        .requestMatchers(HttpMethod.PUT, "/api/management/**").hasAnyAuthority("management:update", "admin:update")
                        .requestMatchers(HttpMethod.DELETE, "/api/management/**").hasAnyAuthority("management:delete", "admin:delete")

                        // Routes utilisateur
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority("user:read", "admin:read", "management:read")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyAuthority("user:update", "admin:update", "management:update")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyAuthority("user:delete", "admin:delete", "management:delete")

                        // Routes de mise à jour des rôles
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}/add-role").hasAuthority("admin:update")
                        .requestMatchers(HttpMethod.PUT, "/api/users/{id}/remove-role").hasAuthority("admin:update")

                        // Routes par défaut, authentification nécessaire
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Utilisation de JWT, donc pas de session
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8005")); // Ajustez selon vos besoins
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Ajoutez d'autres méthodes si nécessaire
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
