package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductDTO;
import com.ecommerce.backend.model.Product;
import com.ecommerce.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(p.getId(), p.getNombre(), p.getDescripcion(), p.getPrecio(), p.getStock());
    }

    private Product toEntity(ProductDTO dto) {
        return Product.builder()
                .id(dto.id())
                .nombre(dto.nombre())
                .descripcion(dto.descripcion())
                .precio(dto.precio())
                .stock(dto.stock())
                .build();
    }

    @Override
    public ProductDTO create(ProductDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    public ProductDTO getById(Long id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public List<ProductDTO> getAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO update(Long id, ProductDTO dto) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        existing.setNombre(dto.nombre());
        existing.setDescripcion(dto.descripcion());
        existing.setPrecio(dto.precio());
        existing.setStock(dto.stock());
        return toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
