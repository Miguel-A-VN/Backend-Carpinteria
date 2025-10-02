package com.productsService.service;


import com.productsService.dto.CreateProductRequest;
import com.productsService.dto.ProductDto;
import com.productsService.model.Category;
import com.productsService.model.Product;
import com.productsService.repository.ProductRepository;
import com.productsService.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toDto(p);
    }

    @Transactional
    public ProductDto createProduct(CreateProductRequest req) {
        Product p = new Product();
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setImageUrls(req.getImageUrls());
        p.setCreatedAt(LocalDateTime.now());

        Set<Category> cats = new HashSet<>();
        if (req.getCategoryNames() != null) {
            for (String catName : req.getCategoryNames()) {
                Category c = categoryRepository.findByName(catName)
                        .orElseGet(() -> {
                            Category nc = new Category();
                            nc.setName(catName);
                            return categoryRepository.save(nc);
                        });
                cats.add(c);
            }
        }
        p.setCategories(cats);

        Product saved = productRepository.save(p);
        return toDto(saved);
    }

    @Transactional
    public ProductDto updateProduct(Long id, CreateProductRequest req) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        p.setName(req.getName());
        p.setDescription(req.getDescription());

        p.setImageUrls(req.getImageUrls());

        Set<Category> cats = new HashSet<>();
        if (req.getCategoryNames() != null) {
            for (String catName : req.getCategoryNames()) {
                Category c = categoryRepository.findByName(catName)
                        .orElseGet(() -> {
                            Category nc = new Category();
                            nc.setName(catName);
                            return categoryRepository.save(nc);
                        });
                cats.add(c);
            }
        }
        p.setCategories(cats);

        Product updated = productRepository.save(p);
        return toDto(updated);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDto toDto(Product p) {
        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setImageUrls(p.getImageUrls());
        dto.setCategories(p.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));
        return dto;
    }
}
