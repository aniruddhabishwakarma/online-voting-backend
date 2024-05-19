package com.project.System.config;

import com.project.System.model.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .cors().and().csrf().disable().authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        new AntPathRequestMatcher("/auth/login"),
                                        new AntPathRequestMatcher("/auth/register"),
                                        new AntPathRequestMatcher("/api/v2/ping"),
                                        new AntPathRequestMatcher("/swagger-ui/**"),
                                        new AntPathRequestMatcher("/swagger-resources/**"),
                                        new AntPathRequestMatcher("/v3/api-docs/**"),
                                        new AntPathRequestMatcher("/webjars/**"),
                                        new AntPathRequestMatcher("/api/users/**")
                                ).permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/api/admin/**/*"),
                                        new AntPathRequestMatcher("/api/admin/*"),
                                        new AntPathRequestMatcher("/api/admin/**")
//                                        new AntPathRequestMatcher("/api/user/**/*"),
//                                        new AntPathRequestMatcher("/api/user/*"),
//                                        new AntPathRequestMatcher("/api/user/**")
                                ).hasAuthority(Roles.ADMIN.name())
                                .requestMatchers(
                                        new AntPathRequestMatcher("/api/user/**/*"),
                                        new AntPathRequestMatcher("/api/user/*"),
                                        new AntPathRequestMatcher("/api/user/**")
                                ).hasAuthority(Roles.USER.name())
                                .anyRequest().authenticated()
                )
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}


