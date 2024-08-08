package org.project.portfolio.global.config;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import lombok.RequiredArgsConstructor;
import org.project.portfolio.global.common.filter.AccessDeniedFilter;
import org.project.portfolio.global.common.filter.LoginAuthenticationFilter;
import org.project.portfolio.global.common.jwt.JwtAccessDeniedHandler;
import org.project.portfolio.global.common.jwt.JwtAuthenticationEntryPoint;
import org.project.portfolio.global.common.jwt.JwtAuthenticationFilter;
import org.project.portfolio.global.common.jwt.JwtExceptionHandlerFilter;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final AccessDeniedFilter accessDeniedFilter;

    private final String[] SwaggerPatterns = {
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**"
    };

    private final String[] UserPostPatterns = {
            "/api/post/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(SwaggerPatterns).permitAll()
                                .requestMatchers(UserPostPatterns).hasAnyRole("USER", "ADMIN")
                                .anyRequest().permitAll()
                )
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .frameOptions(
                                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                        )
                                        .contentSecurityPolicy(policyConfig ->
                                                policyConfig.policyDirectives(
                                                        "script-src 'self'; " + "img-src 'self'; " +
                                                                "font-src 'self' data:; "
                                                                + "default-src 'self'; " +
                                                                "frame-src 'self'"
                                                )
                                        )
                ).logout(logout -> {
                    logout.clearAuthentication(true)
                            .deleteCookies("JSESSIONID");
                })
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.accessDeniedHandler(jwtAccessDeniedHandler)
                            .authenticationEntryPoint(jwtAuthenticationEntryPoint);
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class)
                .addFilterBefore(accessDeniedFilter, AuthorizationFilter.class);
        return http.build();
    }

    public AbstractAuthenticationProcessingFilter abstractAuthenticationProcessingFilter(
            final AuthenticationManager authenticationManager) {
        return new LoginAuthenticationFilter("/api/signin", authenticationManager);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                );
    }


}
