package com.supermarket.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockLowEvent {
    
    private Long productId;
    private String productName;
    private String categoryName;
    private Integer currentStock;
    private Integer lowStockThreshold;
    private LocalDateTime timestamp;
    private String alertLevel; // WARNING, CRITICAL, OUT_OF_STOCK
}