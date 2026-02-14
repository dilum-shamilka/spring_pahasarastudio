package lk.ijse.pahasarastudiospringfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                // 1. Disable CSRF for Postman/API testing
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configure request permissions for all Pahasara Studio Modules
                .authorizeHttpRequests(auth -> auth
                        // ✅ Auth & User Endpoints (Whitelisting both AuthController & UserController)
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/user/**").permitAll()

                        // ✅ Static Resources
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                        // ✅ Whitelisting all Studio Modules (Synced with your Controller paths)
                        .requestMatchers("/api/v1/bookings/**").permitAll()   // Matches BookingController
                        .requestMatchers("/api/v1/clients/**").permitAll()    // Matches ClientController
                        .requestMatchers("/api/v1/equipment/**").permitAll()  // Matches EquipmentController
                        .requestMatchers("/api/v1/inventory/**").permitAll()  // Matches InventoryController
                        .requestMatchers("/api/v1/invoices/**").permitAll()   // Matches InvoiceController
                        .requestMatchers("/api/v1/payment/**").permitAll()    // Matches PaymentController
                        .requestMatchers("/api/v1/services/**").permitAll()   // Matches StudioServiceController

                        // ✅ Role-based access
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")

                        // Any other request must be authenticated
                        .anyRequest().authenticated()
                )

                // 3. Disable browser-style redirects (Prevents the /login redirect loop)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                // 4. Enable Basic Auth for easier testing in Postman
                .httpBasic(basic -> {});

        return http.build();
    }
}