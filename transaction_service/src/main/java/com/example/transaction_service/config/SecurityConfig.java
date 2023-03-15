package com.example.transaction_service.config;


import com.example.transaction_service.security.JwtTokenFilter;
import com.example.data.model.Role;
import com.example.transaction_service.security.MostSecretFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtTokenFilter jwtTokenFilter;
    private final MostSecretFilter mostSecretFilter;

    private static final String MARKET_ENDPOINT = "/api/transaction/marketplaces/**";
    private static final String ACQUIRE_ENDPOINT = "/api/transaction/acquire/**";
    private static final String AUTH_ENDPOINT = "/api/transaction/auth/**";
    private static final String USER_ENDPOINT = "/api/transaction/users/**";


    public SecurityConfig(JwtTokenFilter jwtTokenFilter, MostSecretFilter mostSecretFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
        this.mostSecretFilter = mostSecretFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(mostSecretFilter, JwtTokenFilter.class)
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AUTH_ENDPOINT).permitAll()
                        .requestMatchers(USER_ENDPOINT).hasAuthority(Role.USER.name())
                        .requestMatchers(HttpMethod.POST, MARKET_ENDPOINT).hasAuthority(Role.MARKET.name())
                        .requestMatchers(ACQUIRE_ENDPOINT).hasAuthority(Role.ACQUIRE.name())
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


}
