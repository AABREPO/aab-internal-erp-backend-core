package com.example.Dashboard2.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

// SecurityConfig.java
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/po_model/model_bulkUpload").permitAll()
                        .requestMatchers("/api/properties/**","/api/tenant-groups/**","/api/agreements/**","/api/payment_mode/**"
                                ,"/api/rental_forms/**","/api/tenantShop/**", "/api/po_model/**", "/api/po_brand/**", "/api/po_category/**"
                                ,"/api/po_itemNames/**","/api/po_type/**","/api/purchase_orders/**","/api/site_incharge/**"
                                ,"/api/stocking_location/**","/api/group_name/**","/api/mapped/category/**","/api/incoming_stock/**"
                                ,"/api/net_stock/**","/api/payments-received/**","/api/weekly-expenses/**","/api/advance_portal/**"
                                ,"/advance_portal/googleUploader/**","/api/claim_payments/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:5173", "https://dashboard.aabuilders.in")); // ✅ specific origins
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
