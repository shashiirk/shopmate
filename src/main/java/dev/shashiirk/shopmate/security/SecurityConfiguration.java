package dev.shashiirk.shopmate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class responsible for configuring Spring Security for the application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JWTFilter jwtFilter;

    public SecurityConfiguration(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Configures security filters and rules for the application.
     *
     * @param http HttpSecurity object for configuring security features
     * @return SecurityFilterChain instance representing the security filter chain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(requestsConfigurer -> requestsConfigurer
                    // Public endpoints (accessible without authentication)
                    .requestMatchers(
                            "/api-docs/**",
                            "/swagger-ui/**",
                            "/api/auth/**"
                    ).permitAll()
                    // User-only endpoints (accessible by ROLE_USER or ROLE_ADMIN)
                    .requestMatchers(
                            "/api/checkout/**",
                            "/api/wishlist/**"
                    ).hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("USER", "ADMIN")
                    // Admin-only endpoints (restricted to ROLE_ADMIN)
                    .requestMatchers(
                            "/api/categories/**",
                            "/api/brands/**",
                            "/api/products/**"
                    ).hasRole("ADMIN")
                    // Catch-all for other endpoints, requiring authentication
                    .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .anonymous(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
