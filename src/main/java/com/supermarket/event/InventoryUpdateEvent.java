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
public class InventoryUpdateEvent {
    
    private Long productId;
    private String productName;
    private Integer previousStock;
    private Integer newStock;
    private Integer quantityChanged;
    private String changeType; // SALE, RESTOCK, ADJUSTMENT
    private String reason;
    private LocalDateTime timestamp;
    private Long orderId; // if related to an order
}