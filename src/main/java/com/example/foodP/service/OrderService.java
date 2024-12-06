package com.example.foodP.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.example.foodP.model.OrderModel;
import com.example.foodP.model.OrderStatusType;
import com.example.foodP.model.RestaurantModel;
import com.example.foodP.repository.OrderRepository;
import com.example.foodP.repository.RestaurantRepository;
import com.example.foodP.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

public class OrderService {
    
    
    
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository){
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public ResponseEntity<String> createOrder(HttpSession session, Long itemId,String token,Date orderCreatedDate){
        Long customerId = JwtUtil.extractUserId(token);
        Optional<RestaurantModel> restaurant = restaurantRepository.findRestaurantByItemId(itemId);
        if(restaurant.isEmpty()){
            return ResponseEntity.status(404).body("Restaurant not found for the item");
        }

        OrderModel order = new OrderModel(customerId,restaurant.get().getId(),OrderStatusType.CREATED,orderCreatedDate);
        orderRepository.save(order);
        return ResponseEntity.ok("Order created successfully");
    }
    public ResponseEntity<String> updateOrderStatus(HttpSession session,String token,Long orderId,String status){
        Optional<OrderModel> orderOp =  orderRepository.findById(orderId);
        if (orderOp.isEmpty()) {
            return ResponseEntity.status(404).body("Order not found for the id : " + orderId);
        }
        OrderModel order = orderOp.get();
        if (!OrderStatusType.isValidStatus(status)) {
            return ResponseEntity.status(400).body("Invalid order status");
        }
        if (!(order.getBusinessId().equals(JwtUtil.extractBusinessId(token)))) {
            return ResponseEntity.status(403).body("Unauthorized access to update order status");
        }
        order.setStatus(OrderStatusType.stringToEnum(status));
        orderRepository.save(order);
        return ResponseEntity.ok("Order status updated successfully");
    }



}
