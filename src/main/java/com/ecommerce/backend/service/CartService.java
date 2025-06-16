package com.ecommerce.backend.service;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.model.User;

import java.util.List;

public interface CartService {
    List<CartItem> getCartItems(User user);
    void addToCart(User user, Long productId, int quantity);
    void removeFromCart(User user, Long cartItemId);
    void clearCart(User user);
}
