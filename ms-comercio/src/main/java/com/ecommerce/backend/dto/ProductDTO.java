package com.ecommerce.backend.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        int stock
) {}
