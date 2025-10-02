package com.productsService.dto;

import lombok.*;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    private List<String> imageUrls;

    private Set<String> categories;
}