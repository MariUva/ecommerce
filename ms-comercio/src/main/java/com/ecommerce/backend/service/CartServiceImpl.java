package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;
import com.ecommerce.backend.service.CartService;
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
        CartItem item = CartItem.builder()
                .product(product)
                .quantity(quantity)
                .user(user)
                .build();
        cartRepository.save(item);
    }

    @Override
    public void removeFromCart(User user, Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }
}
