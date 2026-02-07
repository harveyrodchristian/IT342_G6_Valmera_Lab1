package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "missing_token"));
        }
        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtil.parseToken(token);
            Long userId = Long.parseLong(claims.getSubject());
            Optional<User> userOpt = userRepository.findById(userId);
            if (userOpt.isEmpty()) return ResponseEntity.status(404).body(Map.of("error", "user_not_found"));
            User u = userOpt.get();
            return ResponseEntity.ok(Map.of("id", u.getId(), "email", u.getEmail(), "name", u.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "invalid_token"));
        }
    }
}
