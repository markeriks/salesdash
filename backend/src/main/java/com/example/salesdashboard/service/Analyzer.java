package com.example.salesdashboard.service;

import com.example.salesdashboard.entity.Sale;
import com.example.salesdashboard.repository.SalesRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Analyzer {

    private final SalesRepository salesRepository;

    public Analyzer(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public double getTotalRevenue(String userEmail) {
        List<Sale> sales = salesRepository.findByUserEmail(userEmail);
        return sales.stream()
                .mapToDouble(sale -> sale.getUnitsSold() * sale.getSalePrice())
                .sum();
    }

    public int getTotalUnitsSold(String userEmail) {
        List<Sale> sales = salesRepository.findByUserEmail(userEmail);
        return sales.stream()
                .mapToInt(Sale::getUnitsSold)
                .sum();
    }

    public double getTotalProfit(String userEmail) {
        List<Sale> sales = salesRepository.findByUserEmail(userEmail);
        return sales.stream()
                .mapToDouble(sale -> {
                    double gross = sale.getSalePrice();
                    double tax = gross * 0.24;
                    double netProfitPerUnit = gross - tax - sale.getCostPerUnit();
                    return netProfitPerUnit * sale.getUnitsSold();
                })
                .sum();
    }

    public double getTotalTax(String userEmail) {
        return getTotalRevenue(userEmail) * 0.24;
    }

    public Double getTotalCostOfGoods(String userEmail) {
        List<Sale> sales = salesRepository.findByUserEmail(userEmail);
        return sales.stream()
                .mapToDouble(sale -> sale.getCostPerUnit() * sale.getUnitsSold())
                .sum();
    }

}
