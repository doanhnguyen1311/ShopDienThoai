package com.doanh.ShopDienThoai.security;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // phan quyen
public class SecurityConfig {
  private final String[] PUBLIC_ENDPOINT = {
    "/users/add", "/auth/token", "/auth/introspect", "/auth/logout", "/auth/refresh"
  };

  @Autowired private CustomJWTDecoder customJWTDecoder;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(
        request ->
            request
                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT)
                .permitAll() // cho phep truy cap ma khong can security
                //                                .requestMatchers(HttpMethod.GET, "/users")
                //                                .hasAnyAuthority("SCOPE_" + Roles.ADMIN.name())
                .anyRequest()
                .authenticated() // các yêu cầu khác đều cần phải xác thực
        );
    http.oauth2ResourceServer(
            oauth2 ->
                oauth2.jwt(
                    jwtConfigurer ->
                        jwtConfigurer.decoder(customJWTDecoder) // de biet token hop le hay khong
                    ))
        .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // Tạo một PasswordEncoder bean sử dụng BCrypt
  }
}
