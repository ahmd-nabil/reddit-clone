package com.redditclone.controllers;

import com.redditclone.dto.SignupRequest;
import com.redditclone.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequest signupRequest) {
        authService.signup(signupRequest);
    }

    @GetMapping("")
    public void verify(@RequestParam String token) {
        authService.verify(token);
    }
}
