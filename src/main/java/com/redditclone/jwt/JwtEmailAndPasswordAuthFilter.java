package com.redditclone.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redditclone.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtEmailAndPasswordAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtConfig jwtConfig;

    public JwtEmailAndPasswordAuthFilter(String loginUrl, AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        super(loginUrl, authenticationManager);
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest loginRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequest.class);
            Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authenticationResult = super.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
            return authenticationResult;
        } catch (IOException e) {
            throw new IllegalStateException("Not Authenticated, cant read email and password");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String jwtToken = jwtConfig.generateToken(authResult);
        response.addHeader(jwtConfig.getHeader(), jwtConfig.getTokenPrefix() + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.addHeader(jwtConfig.getHeader(), "failure");
    }
}
