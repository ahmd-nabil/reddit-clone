package com.redditclone.services;

import com.redditclone.entities.User;
import com.redditclone.exceptions.RedditException;
import com.redditclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalStateException("incorrect username or password"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new RedditException("User not found!"));
    }

    public User save(User user) {
        User oldUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if(oldUser != null) {
            throw new RedditException("Username already exists");
        }
        oldUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(oldUser != null) {
            throw new RedditException("There is an account connected with this email");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
