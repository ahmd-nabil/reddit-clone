package com.redditclone.jwt;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final JwtConfig jwtConfig;
//
//    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, String url) {
//        super.setAuthenticationManager(authenticationManager);
//        this.jwtConfig = jwtConfig;
//        setFilterProcessesUrl(url);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        try {
//            LoginRequest loginRequest = new ObjectMapper()
//                    .readValue(request.getInputStream(), LoginRequest.class);
//            Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
//            Authentication authenticationResult = super.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
//            return authenticationResult;
//        } catch (IOException e) {
//            throw new IllegalStateException("Not Authenticated, cant find email and password");
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
//        String jwtToken = jwtConfig.generateToken(authResult);
//        response.addHeader(jwtConfig.getHeader(), jwtConfig.getTokenPrefix() + jwtToken);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        response.addHeader(jwtConfig.getHeader(), "failure");
//    }
}
