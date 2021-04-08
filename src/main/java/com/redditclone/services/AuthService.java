package com.redditclone.services;

import com.redditclone.dto.SignupRequest;
import com.redditclone.entities.User;
import com.redditclone.entities.VerificationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {
    private final String BASE_VERIFICATION_LINK = "http://localhost:8080/api/auth?token=";
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final EmailValidator emailValidator;
    private final VerificationTokenService verificationTokenService;
    private final MailBuilder mailBuilder;
    private final MailService mailService;

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
            throw new IllegalStateException("Not valid email.");

        // make sure there is no old user with the same email
        UserDetails oldUser = userService.findByEmail(signupRequest.getEmail());
        if(oldUser != null)
            throw new IllegalStateException("There is already an account with this email");
    }
}
