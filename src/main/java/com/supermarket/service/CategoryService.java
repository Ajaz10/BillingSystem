package com.supermarket.service;

import com.supermarket.dto.CategoryDto;
import com.supermarket.entity.Category;
import com.supermarket.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Cacheable(value = "categories")
    public List<CategoryDto> getAllActiveCategories() {
        return categoryRepository.findByIsActive(true).stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(CategoryDto::fromEntity);
    }

    @Cacheable(value = "category", key = "#id")
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return CategoryDto.fromEntity(category);
    }

    public Page<CategoryDto> searchCategories(String search, Pageable pageable) {
        return categoryRepository.findBySearchTerm(search, pageable)
                .map(CategoryDto::fromEntity);
    }

    @Transactional
    @CacheEvict(value = {"categories", "category"}, allEntries = true)
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new RuntimeException("Category with name already exists: " + categoryDto.getName());
        }

        Category category = Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .isActive(true)
                .build();

        Category savedCategory = categoryRepository.save(category);
        log.info("Category created: {}", savedCategory.getName());
        
        return CategoryDto.fromEntity(savedCategory);
    }

    @Transactional
    @CacheEvict(value = {"categories", "category"}, allEntries = true)
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        // Check if name is being changed and if it already exists
        if (categoryDto.getName() != null && 
            !categoryDto.getName().equals(existingCategory.getName()) &&
            categoryRepository.existsByName(categoryDto.getName())) {
            throw new RuntimeException("Category with name already exists: " + categoryDto.getName());
        }

        if (categoryDto.getName() != null) existingCategory.setName(categoryDto.getName());
        if (categoryDto.getDescription() != null) existingCategory.setDescription(categoryDto.getDescription());
        if (categoryDto.getIsActive() != null) existingCategory.setIsActive(categoryDto.getIsActive());

        Category updatedCategory = categoryRepository.save(existingCategory);
        log.info("Category updated: {}", updatedCategory.getName());
        
        return CategoryDto.fromEntity(updatedCategory);
    }

    @Transactional
    @CacheEvict(value = {"categories", "category"}, allEntries = true)
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        
        // Check if category has products
        if (!category.getProducts().isEmpty()) {
            throw new RuntimeException("Cannot delete category with existing products");
        }
        
        category.setIsActive(false);
        categoryRepository.save(category);
        log.info("Category deactivated: {}", category.getName());
    }
}