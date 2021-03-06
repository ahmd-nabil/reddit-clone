package com.redditclone.services;

import com.redditclone.dto.Jwt;
import com.redditclone.dto.LoginRequest;
import com.redditclone.dto.SignupRequest;
import com.redditclone.entities.User;
import com.redditclone.entities.UserRole;
import com.redditclone.entities.VerificationToken;
import com.redditclone.jwt.JwtConfig;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {
    private final String BASE_VERIFICATION_LINK = "http://localhost:8080/api/auth/verify?token=";
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailValidator emailValidator;
    private final VerificationTokenService verificationTokenService;
    private final MailBuilder mailBuilder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        validateSignupRequest(signupRequest);
        // Create a valid user TODO refactor to convertors
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setCreatedTime(Instant.now());
        user.setEnabled(false);
        user.setUserRole(UserRole.USER);

        // save user token in DB (order matters)
        userService.save(user);
        //create verification token for that user
        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user);

        // send verification mail to user's email
        String verificationLink = BASE_VERIFICATION_LINK + verificationToken.getToken();
        mailService.sendMail(user.getEmail(), mailBuilder.build(user.getUsername(), verificationLink));
    }

    private void validateSignupRequest(SignupRequest signupRequest) {
        // make sure email is valid
        if(!emailValidator.valid(signupRequest.getEmail()))
            throw new IllegalStateException("Not Valid email.");
    }

    public Authentication getCurrentUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Jwt login(LoginRequest loginRequest) {
        Authentication authResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        if(authResult.isAuthenticated()) {
            String token = jwtConfig.getTokenPrefix() + jwtConfig.generateToken(authResult);
            return new Jwt(loginRequest.getUsername(), token);
        }
        return new Jwt(null,null);
    }
}
