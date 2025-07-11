package com.example.salesdashboard.controller;

import com.example.salesdashboard.entity.Sale;
import com.example.salesdashboard.security.JwtUtil;
import com.example.salesdashboard.service.SalesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<Sale>> getSalesForUser(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }

        String token = authHeader.substring(7);
        String userEmail;
        try {
            userEmail = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }

        List<Sale> sales = salesService.getSalesForUser(userEmail);
        return ResponseEntity.ok(sales);
    }
}
