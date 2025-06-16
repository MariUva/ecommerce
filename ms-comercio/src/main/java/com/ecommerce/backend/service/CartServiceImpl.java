package com.ecommerce.backend.service;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CartItem> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public void addToCart(User user, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        CartItem item = CartItem.builder()
                .product(product)
                .quantity(quantity)
                .user(user)
                .build();

        cartRepository.save(item);
    }

    @Override
    @Transactional
    public void removeFromCart(User user, Long cartItemId) {
        CartItem item = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        Product product = item.getProduct();
        product.setStock(product.getStock() + item.getQuantity());
        productRepository.save(product);

        cartRepository.deleteById(cartItemId);
    }

    @Override
    @Transactional
    public void clearCart(User user) {
        List<CartItem> items = cartRepository.findByUser(user);

        for (CartItem item : items) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        cartRepository.deleteByUser(user);
    }
}
