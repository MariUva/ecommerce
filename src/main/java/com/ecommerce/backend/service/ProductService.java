package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO create(ProductDTO dto);
    ProductDTO getById(Long id);
    List<ProductDTO> getAll();
    ProductDTO update(Long id, ProductDTO dto);
    void delete(Long id);
}
