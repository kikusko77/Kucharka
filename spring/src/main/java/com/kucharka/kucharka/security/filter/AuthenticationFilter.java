package com.kucharka.kucharka.security.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kucharka.kucharka.DTO.AuthenticationRequest;
import com.kucharka.kucharka.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kucharka.kucharka.entity.User;
import com.kucharka.kucharka.security.SecurityConstants;
import com.kucharka.kucharka.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private CustomAuthenticationManager authenticationManager;
    private final UserService userService;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            AuthenticationRequest authRequest = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(failed.getMessage());
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = userService.getUser(authResult.getName());
        Collection<? extends GrantedAuthority> authorities = userService.getAuthorities(user);
        String role = authorities.stream().findFirst().map(GrantedAuthority::getAuthority).orElse("").replace("ROLE_", "");

        // Get the user ID
        Long userId = user.getId();

        String token = JWT.create()
                .withSubject(authResult.getName())
                .withClaim("role", role) // Include the role as a claim
                .withClaim("userId", userId) // Include the user ID as a claim
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
    }
}
