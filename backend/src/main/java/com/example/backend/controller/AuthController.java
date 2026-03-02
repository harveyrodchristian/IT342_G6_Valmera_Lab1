package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import com.example.backend.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");
        User u = authService.register(name, email, password);
        return ResponseEntity.ok(Map.of("id", u.getId(), "email", u.getEmail(), "name", u.getName()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        Optional<User> userOpt = authService.authenticate(email, password);
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body(Map.of("error", "invalid_credentials"));
        User u = userOpt.get();
        String token = JwtUtil.generateToken(u.getId(), u.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        // In JWT-based systems, logout is primarily client-side (delete the token)
        // This endpoint just confirms the logout action
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "missing_token"));
        }
        return ResponseEntity.ok(Map.of("message", "logged_out_successfully"));
    }
}
