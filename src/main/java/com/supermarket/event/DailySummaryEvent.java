package com.supermarket.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySummaryEvent {
    
    private LocalDate summaryDate;
    private BigDecimal totalSales;
    private Long totalOrders;
    private Long totalCustomers;
    private BigDecimal averageOrderValue;
    private List<CategorySummary> categorySales;
    private List<ProductSummary> topSellingProducts;
    private LocalDateTime generatedAt;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CategorySummary {
        private String categoryName;
        private BigDecimal totalSales;
        private Long totalQuantitySold;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductSummary {
        private String productName;
        private String categoryName;
        private Integer quantitySold;
        private BigDecimal totalSales;
    }
}