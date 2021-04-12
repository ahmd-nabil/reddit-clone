package com.redditclone.services;

import com.redditclone.entities.User;
import com.redditclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalStateException("incorrect username or password"));
    }

    public User save(User user) {
        User oldUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if(oldUser != null) {
            throw new IllegalStateException("There Username already exists");
        }
        oldUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(oldUser != null) {
            throw new IllegalStateException("There is already an account with this email");
        }
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
