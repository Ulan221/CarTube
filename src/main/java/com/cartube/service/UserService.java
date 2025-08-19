package com.cartube.service;

import com.cartube.entity.User;
import com.cartube.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(final String username, final String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username is already in use");
        }

        final User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        userRepository.save(user);
    }

    public User findUser(final String username) {
        return userRepository.findByUsername(username);
    }
}
