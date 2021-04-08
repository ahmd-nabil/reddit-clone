package com.redditclone.controllers;

import com.redditclone.dto.SignupRequest;
import com.redditclone.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
        return new ResponseEntity<String>("Signed Up Successfully", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<String> verify(@RequestParam String token) {
        authService.verify(token);
        return new ResponseEntity<String>("Account Verified", HttpStatus.OK);
    }
}
