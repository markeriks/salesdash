package com.example.salesdashboard.controller;

import com.example.salesdashboard.dto.LoginRequest;
import com.example.salesdashboard.dto.SignupRequest;
import com.example.salesdashboard.security.JwtUtil;
import com.example.salesdashboard.service.UserServiceLogin;
import com.example.salesdashboard.service.UserServiceSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserServiceSignup userServiceSignup;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserServiceLogin userServiceLogin;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            userServiceSignup.registerUser(request);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            if(userServiceLogin.checkPasswordMatch(request)) {
                final String jwt = jwtUtil.generateToken(request.getEmail());
                return ResponseEntity.ok(Map.of("token", jwt));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }
    }
}
