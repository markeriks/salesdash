package com.example.salesdashboard.service;

import com.example.salesdashboard.entity.Sale;
import com.example.salesdashboard.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private Analyzer analyzer;

    public List<Sale> getSalesForUser(String email) {
        return salesRepository.findByUserEmail(email);
    }

    public void saveSales(List<Sale> sales) {
        salesRepository.saveAll(sales);
    }

    public Map<String, Object> getMetricsForUser(String email) {
        double totalRevenue = analyzer.getTotalRevenue(email);
        int totalUnits = analyzer.getTotalUnitsSold(email);
        double totalProfit = analyzer.getTotalProfit(email);
        double totalTax = analyzer.getTotalTax(email);
        double totalCost = analyzer.getTotalCostOfGoods(email);

        return Map.of(
                "totalRevenue", totalRevenue,
                "totalUnitsSold", totalUnits,
                "totalProfit", totalProfit,
                "totalTax", totalTax,
                "totalCost", totalCost
        );
    }
}
