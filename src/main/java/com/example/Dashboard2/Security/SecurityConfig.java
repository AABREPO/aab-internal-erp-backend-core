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
                        .requestMatchers(HttpMethod.PUT, "/api/weekly-expenses/update/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/daily-payments/send-to-expenses/**").permitAll()
                        .requestMatchers("/api/properties/**","/api/tenant-groups/**","/api/agreements/**","/api/payment_mode/**"
                                ,"/api/rental_forms/**","/api/tenantShop/**", "/api/po_model/**", "/api/po_brand/**", "/api/po_category/**"
                                ,"/api/po_itemNames/**","/api/po_type/**","/api/purchase_orders/**","/api/site_incharge/**"
                                ,"/api/stocking_location/**","/api/group_name/**","/api/mapped/category/**","/api/incoming_stock/**"
                                ,"/api/net_stock/**","/api/payments-received/**","/api/weekly-expenses/**","/api/advance_portal/**"
                                ,"/advance_portal/googleUploader/**","/api/claim_payments/**","/api/weekly_payment_audit/**"
                                ,"/api/weekly_types/**","/api/weekly_received_types/**","/api/labours-details/**","/api/daily-payments/**"
                                ,"/api/refund_received/**","/api/daily_entry_audit/**","/api/employee_details/**","/api/cash-register/**"
                                ,"/api/purposes/**","/api/staff-advance/**","/api/loans/**","/api/vendor-payments/**","/api/loan-purposes/**"
                                ,"/api/rent-history/**","/api/weekly-payment-bills/**","/api/account-details/**", "/api/bank_type/**"
                                ,"/api/bill-entry/**","/api/vendor-bill-tracker/**","/api/invoices/**","/api/eb-service-no/**","/api/support_staff/**"
                                ,"/api/utility-telecom-purpose/**","/api/utility-telecom-service-provider/**","/api/utility-telecom-service-type/**"
                                ,"/api/subscription-service-type/**","/api/subscription-service-provider/**","/api/subscription-purpose/**"
                                ,"/api/amc-service-type/**","/api/amc-service-provider/**","/api/amc-purpose/**","/api/utility-telecom/**","/api/incoming_pdfs/**"
                                ,"/api/utility-subscription/**","/api/utility-frequency/**","/api/tenant_link_shop/**","/api/edit_requests/**","/api/inventory/**"
                                ,"/api/data_correction/**","/api/data_correction/monitoring/{tableName}/{dataId}","/api/vendor_carry_forward/**","/api/tools_tracker_stock_management/**"
                        ,"/api/closed_po_records/**","/api/tools_item_name/**","/api/tools_tracker_management/**","/api/tools_brand/**","/api/tools_item_id/**"
                        ,"/api/tools_tracker_management/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:5173", "https://dashboard.aabuilders.in","https://po.aabuilders.in","https://orbit.aabuilders.in")); // ✅ specific origins
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("*"));
        config.addAllowedHeader("*");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
