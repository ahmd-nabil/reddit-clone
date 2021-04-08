package services;

import com.redditclone.dto.SignupRequest;
import com.redditclone.entities.User;
import com.redditclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EmailValidator emailValidator;

    @Transactional
    public void signup(SignupRequest signupRequest) {
        validateSignupRequest(signupRequest);
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setCreatedTime(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
    }

    private void validateSignupRequest(SignupRequest signupRequest) {
        // make sure email is valid
        if(!emailValidator.valid(signupRequest.getEmail()))
            throw new IllegalStateException("Not valid email.");

        // make sure there is no old user with the same email
        User oldUser = userRepository.findByEmail(signupRequest.getEmail()).orElse(null);
        if(oldUser != null)
            throw new IllegalStateException("There is already an account with this email");
    }
}
