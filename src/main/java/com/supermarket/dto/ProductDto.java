package com.supermarket.dto;

import com.supermarket.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private BigDecimal discount;
    private String imageUrl;
    private Integer lowStockThreshold;
    private Boolean isActive;
    private CategoryDto category;
    private BigDecimal discountedPrice;
    private Boolean isLowStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .discount(product.getDiscount())
                .imageUrl(product.getImageUrl())
                .lowStockThreshold(product.getLowStockThreshold())
                .isActive(product.getIsActive())
                .category(CategoryDto.fromEntity(product.getCategory()))
                .discountedPrice(product.getDiscountedPrice())
                .isLowStock(product.isLowStock())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}