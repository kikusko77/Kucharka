package com.kucharka.Kucharka.security;


import com.kucharka.Kucharka.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import com.kucharka.Kucharka.security.filter.AuthenticationFilter;
import com.kucharka.Kucharka.security.filter.ExceptionHandlerFilter;
import com.kucharka.Kucharka.security.filter.JWTAuthorizationFilter;
import com.kucharka.Kucharka.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final UserService userService;



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager,userService);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
//                .headers().frameOptions().disable() // New Line: the h2 console runs on a "frame". By default, Spring Security prevents rendering within an iframe. This line disables its prevention.
//                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**","/groceries/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

}
