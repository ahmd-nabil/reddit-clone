package com.redditclone.services;

import com.redditclone.entities.User;
import com.redditclone.entities.VerificationToken;
import com.redditclone.repositories.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationToken generateVerificationToken(User user) {
        VerificationToken verificationToken = VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plus(30, ChronoUnit.MINUTES))
                .build();
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public boolean verify(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(()->new IllegalStateException("Token not found"));
        if(verificationToken.getExpiryDate().isBefore(Instant.now()))
            throw new IllegalStateException("Token has expired");

        User user = verificationToken.getUser();
        if(user.isEnabled())
            throw new IllegalStateException("User already verified");

        user.setEnabled(true);
        return user.isEnabled();
    }
}
