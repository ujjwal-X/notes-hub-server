package com.noteshub.service;

import com.noteshub.dto.LoginRequest;
import com.noteshub.dto.LoginResponse;
import com.noteshub.dto.SignupRequest;
import com.noteshub.entity.User;
import com.noteshub.repository.UserRepository;
import com.noteshub.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "❌ Email already registered.";
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // ✅ Check if role is provided, else set default role as "USER"
        String role = request.getRole();
        if (role == null || role.isBlank()) {
            role = "USER";
        }
        user.setRole(role);

        userRepository.save(user);
        return "✅ User registered successfully!";
    }

    public LoginResponse login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        String token = jwtService.generateToken((UserDetails) user);
                        return new LoginResponse("Login successful", token);
                    } else {
                        return new LoginResponse("❌ Invalid credentials", null);
                    }
                })
                .orElse(new LoginResponse("❌ User not found", null));
    }
}
