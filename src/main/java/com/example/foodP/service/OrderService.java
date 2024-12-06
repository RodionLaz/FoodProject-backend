package com.example.foodP.service;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.foodP.model.OrderModel;
import com.example.foodP.model.OrderStatusType;
import com.example.foodP.model.RestaurantModel;
import com.example.foodP.repository.OrderRepository;
import com.example.foodP.repository.RestaurantRepository;
import com.example.foodP.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;

/**
 * Service class for managing orders.
 */
public class OrderService {
    
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;

    /**
     * Autowired constructor to inject dependencies.
     * 
     * @param orderRepository The repository for managing orders.
     * @param restaurantRepository The repository for managing restaurants.
     */
    @Autowired
    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository){
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Creates a new order.
     * 
     * @param session The HTTP session.
     * @param itemId The ID of the item to be ordered.
     * @param token The authentication token.
     * @param orderCreatedDate The date the order was created.
     * @return A ResponseEntity indicating the outcome of the operation.
     */
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
    /**
     * Updates the status of an existing order.
     * 
     * @param session The HTTP session.
     * @param token The authentication token.
     * @param orderId The ID of the order to be updated.
     * @param status The new status of the order.
     * @return A ResponseEntity indicating the outcome of the operation.
     */
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
    /**
     * Retrieves order information by order ID.
     * 
     * @param session The HTTP session.
     * @param token The authentication token.
     * @param orderId The ID of the order to retrieve information for.
     * @return A ResponseEntity containing the order information.
     */
    public ResponseEntity<String> getOrderInfoById(HttpSession session,String token,Long orderId){
        Optional<OrderModel> orderOp =  orderRepository.findById(orderId);
        if (orderOp.isEmpty()) {
            return ResponseEntity.status(404).body("Order not found for the id : " + orderId);
        }
        OrderModel order = orderOp.get();
        if ((!order.getCustomerId().equals(JwtUtil.extractUserId(token))) && 
            !(order.getBusinessId().equals(JwtUtil.extractBusinessId(token)))) {
            return ResponseEntity.status(403).body("Unauthorized access to view order information");
        }
        try {
            String orderJson = new ObjectMapper().writeValueAsString(order);
            return ResponseEntity.status(200).body(orderJson);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing order information");
        }
    }



}
