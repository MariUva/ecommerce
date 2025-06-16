package com.ecommerce.backend.controller;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.User;
import com.ecommerce.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public Order placeOrder(@AuthenticationPrincipal User user) {
        return orderService.placeOrder(user);
    }

    @GetMapping("/history")
    public List<Order> getOrderHistory(@AuthenticationPrincipal User user) {
        return orderService.getOrdersByUser(user);
    }
}
