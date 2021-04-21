package com.redditclone.controllers;

import com.redditclone.dto.Jwt;
import com.redditclone.dto.LoginRequest;
import com.redditclone.dto.SignupRequest;
import com.redditclone.services.AuthService;
import com.redditclone.services.VerificationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final VerificationTokenService verificationTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return new ResponseEntity<String>("Signed Up Successfully", HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        verificationTokenService.verify(token);
        return new ResponseEntity<String>("Account Verified", HttpStatus.OK);
    }

    @PostMapping("/login")
    public Jwt login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
