package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String email, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword);
        User u = new User(email, hashed, name);
        return userRepository.save(u);
    }

    public Optional<User> authenticate(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User u = userOpt.get();
            if (passwordEncoder.matches(rawPassword, u.getPassword())) return Optional.of(u);
        }
        return Optional.empty();
    }
}
