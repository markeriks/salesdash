package com.example.salesdashboard.service;

import com.example.salesdashboard.dto.LoginRequest;
import com.example.salesdashboard.entity.User;
import com.example.salesdashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceLogin implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkPasswordMatch(LoginRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Invalid email!");
        }
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        String encryptedPassword = "";
        if (user.isPresent()) {
            encryptedPassword = user.get().getPassword();
        }
        return passwordEncoder.matches(request.getPassword(), encryptedPassword);
    }
}
