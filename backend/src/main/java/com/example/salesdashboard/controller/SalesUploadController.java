package com.example.salesdashboard.controller;

import com.example.salesdashboard.entity.Sale;
import com.example.salesdashboard.security.JwtUtil;
import com.example.salesdashboard.service.SalesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SalesUploadController {

    @Autowired
    private SalesService salesService;

    @Autowired
    private JwtUtil jwtUtil;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        try {

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Missing or invalid Authorization header");
            }
            String token = authHeader.substring(7);
            String email = jwtUtil.extractEmail(token);


            List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines()
                    .toList();

            List<Sale> sales = lines.stream()
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split("\t");
                        Sale sale = new Sale();
                        sale.setRowNo(Integer.parseInt(parts[0]));
                        sale.setOrderDate(LocalDate.parse(parts[1], formatter));
                        sale.setCountry(parts[2]);
                        sale.setProductId(parts[3]);
                        sale.setCategory(parts[4]);
                        sale.setProductName(parts[5]);
                        sale.setCostPerUnit(Double.parseDouble(parts[6].replace(",", ".")));
                        sale.setUnitsSold(Integer.parseInt(parts[7]));
                        sale.setSalePrice(Double.parseDouble(parts[8].replace(",", ".")));
                        sale.setUserEmail(email);
                        return sale;
                    })
                    .toList();
            salesService.saveSales(sales);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to process file: " + e.getMessage());
        }
    }
}
