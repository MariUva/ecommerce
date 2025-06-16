package com.ecommerce.backend.service;

import com.ecommerce.backend.model.*;
import com.ecommerce.backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) throw new RuntimeException("Carrito vacío");

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        List<OrderItem> orderItems = cartItems.stream().map(item -> {
            Product product = item.getProduct();

            BigDecimal itemPrice = product.getPrecio();

            return OrderItem.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .price(itemPrice)
                    .order(order)
                    .build();
        }).toList();

        BigDecimal total = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setItems(orderItems);
        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);
        cartRepository.deleteByUser(user);
        return savedOrder;
    }

    @Override
    @Transactional
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
