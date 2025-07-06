package com.supermarket.service;

import com.supermarket.dto.ProductDto;
import com.supermarket.entity.Category;
import com.supermarket.entity.Product;
import com.supermarket.repository.CategoryRepository;
import com.supermarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Cacheable(value = "products", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findByIsActive(true, pageable)
                .map(ProductDto::fromEntity);
    }

    @Cacheable(value = "product", key = "#id")
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return ProductDto.fromEntity(product);
    }

    public Page<ProductDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndIsActive(categoryId, true, pageable)
                .map(ProductDto::fromEntity);
    }

    public Page<ProductDto> searchProducts(String search, Pageable pageable) {
        return productRepository.findBySearchTerm(search, pageable)
                .map(ProductDto::fromEntity);
    }

    public Page<ProductDto> filterProducts(String search, Long categoryId, 
                                         BigDecimal minPrice, BigDecimal maxPrice, 
                                         Pageable pageable) {
        return productRepository.findProductsByFilters(search, categoryId, minPrice, maxPrice, pageable)
                .map(ProductDto::fromEntity);
    }

    public Page<ProductDto> getDiscountedProducts(Pageable pageable) {
        return productRepository.findDiscountedProducts(pageable)
                .map(ProductDto::fromEntity);
    }

    public List<ProductDto> getLowStockProducts() {
        return productRepository.findLowStockProducts().stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = {"products", "product"}, allEntries = true)
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .discount(productDto.getDiscount() != null ? productDto.getDiscount() : BigDecimal.ZERO)
                .imageUrl(productDto.getImageUrl())
                .lowStockThreshold(productDto.getLowStockThreshold() != null ? productDto.getLowStockThreshold() : 10)
                .isActive(true)
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        log.info("Product created: {}", savedProduct.getName());
        
        return ProductDto.fromEntity(savedProduct);
    }

    @Transactional
    @CacheEvict(value = {"products", "product"}, allEntries = true)
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (productDto.getCategory() != null && productDto.getCategory().getId() != null) {
            Category category = categoryRepository.findById(productDto.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingProduct.setCategory(category);
        }

        if (productDto.getName() != null) existingProduct.setName(productDto.getName());
        if (productDto.getDescription() != null) existingProduct.setDescription(productDto.getDescription());
        if (productDto.getPrice() != null) existingProduct.setPrice(productDto.getPrice());
        if (productDto.getStock() != null) existingProduct.setStock(productDto.getStock());
        if (productDto.getDiscount() != null) existingProduct.setDiscount(productDto.getDiscount());
        if (productDto.getImageUrl() != null) existingProduct.setImageUrl(productDto.getImageUrl());
        if (productDto.getLowStockThreshold() != null) existingProduct.setLowStockThreshold(productDto.getLowStockThreshold());
        if (productDto.getIsActive() != null) existingProduct.setIsActive(productDto.getIsActive());

        Product updatedProduct = productRepository.save(existingProduct);
        log.info("Product updated: {}", updatedProduct.getName());
        
        return ProductDto.fromEntity(updatedProduct);
    }

    @Transactional
    @CacheEvict(value = {"products", "product"}, allEntries = true)
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setIsActive(false);
        productRepository.save(product);
        log.info("Product deactivated: {}", product.getName());
    }

    @Transactional
    public void updateStock(Long productId, Integer newStock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        Integer oldStock = product.getStock();
        product.setStock(newStock);
        productRepository.save(product);
        
        log.info("Stock updated for product {}: {} -> {}", product.getName(), oldStock, newStock);
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }
        
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        
        log.info("Stock decreased for product {}: -{} (new stock: {})", 
                product.getName(), quantity, product.getStock());
    }
}