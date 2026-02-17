package lk.ijse.pahasarastudiospringfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http)) // Enable CORS support
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()   // Login endpoint
                        .requestMatchers("/api/v1/users/save").permitAll() // Signup endpoint
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/api/v1/email/**").permitAll()
                        .requestMatchers("/api/v1/clients/**").permitAll()
                        .requestMatchers("/api/v1/bookings/**").permitAll()
                        .requestMatchers("/api/v1/inventory/**").permitAll()
                        .requestMatchers("/api/v1/equipment/**").permitAll()
                        .requestMatchers("/api/v1/services/**").permitAll()
                        .requestMatchers("/api/v1/invoices/**").permitAll()
                        // Inside SecurityConfig.java -> filterChain method
                        .requestMatchers("/api/v1/costs/**").permitAll()
                        .requestMatchers("/api/v1/albums/**").permitAll()
                        //.requestMatchers("/api/v1/albums/upload/**").permitAll()
                       //.requestMatchers("/api/v1/albums/all").permitAll()
                        .requestMatchers("/api/v1/payment/**").permitAll()
                        .requestMatchers("/api/v1/photographers/**").permitAll()
                        .requestMatchers("/css/**","/js/**","/images/**").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(basic -> {});

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}