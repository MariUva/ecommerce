package com.ecommerce.backend.service;

import com.ecommerce.backend.model.Order;
import com.ecommerce.backend.model.User;

import java.util.List;

public interface OrderService {
    Order placeOrder(User user);
    List<Order> getOrdersByUser(User user);
}
