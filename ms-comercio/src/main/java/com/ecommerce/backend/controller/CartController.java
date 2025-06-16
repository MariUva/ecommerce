package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.CartItem;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCartItems(@AuthenticationPrincipal User user) {
        return cartService.getCartItems(user);
    }

    @PostMapping("/add")
    public void addToCart(@AuthenticationPrincipal User user,
                          @RequestParam Long productId,
                          @RequestParam int quantity) {
        cartService.addToCart(user, productId, quantity);
    }

    @DeleteMapping("/remove/{itemId}")
    public void removeFromCart(@AuthenticationPrincipal User user,
                               @PathVariable Long itemId) {
        cartService.removeFromCart(user, itemId);
    }

    @DeleteMapping("/clear")
    public void clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user);
    }
}
