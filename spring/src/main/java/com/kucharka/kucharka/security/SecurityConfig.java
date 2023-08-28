package com.kucharka.kucharka.security;


import com.kucharka.kucharka.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.kucharka.kucharka.security.filter.AuthenticationFilter;
import com.kucharka.kucharka.security.filter.ExceptionHandlerFilter;
import com.kucharka.kucharka.security.filter.JWTAuthorizationFilter;
import com.kucharka.kucharka.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserService userService;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedOrigins(Arrays.asList("https://kucharka.vercel.app"));
        configuration.setAllowedOrigins(Arrays.asList("https://kucharka-gumimaco.vercel.app"));
        configuration.setAllowedOrigins(Arrays.asList("https://kucharka-git-main-gumimaco.vercel.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager,userService);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
//                .headers().frameOptions().disable() // New Line: the h2 console runs on a "frame". By default, Spring Security prevents rendering within an iframe. This line disables its prevention.
//                .and()
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/admin/**", "/groceries/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/admin/**", "/groceries/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/admin/**", "/groceries/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
