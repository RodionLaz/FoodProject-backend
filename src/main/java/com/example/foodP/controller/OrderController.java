package com.example.foodP.controller;

import java.sql.Date;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodP.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
public class OrderController {
    
    private static final String AUTH_REQUIRED_MESSAGE = "Authorization is required";
    private static final String BEARER_PREFIX = "Bearer ";
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a new order asynchronously.
     * 
     * @param authorizationHeader The authorization header containing the token.
     * @param session The HTTP session.
     * @param itemId The ID of the item to be ordered.
     * @return A CompletableFuture of ResponseEntity containing the result of the operation.
     */
    @Async("taskExecutor")
    @PostMapping("/api/order/create")
    public CompletableFuture<ResponseEntity<String>> createOrder(
    @RequestHeader("Authorization") String authorizationHeader,
    HttpSession session,
    @RequestParam  Long itemId ){
        try{
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                return CompletableFuture.completedFuture(new ResponseEntity<>(AUTH_REQUIRED_MESSAGE, HttpStatus.FORBIDDEN));
            }
            if (itemId <= 0) {
                return CompletableFuture.completedFuture(new ResponseEntity<>("Invalid item ID", HttpStatus.BAD_REQUEST));
            }
            String token = authorizationHeader.replace(BEARER_PREFIX, "");
            ResponseEntity<String> response = orderService.createOrder(session, itemId,token,new Date(System.currentTimeMillis()));
            return CompletableFuture.completedFuture(response);
        }catch(Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order: " + e.getMessage())); 
        }
    }

    /**
     * Updates the status of an existing order asynchronously.
     * 
     * @param authorizationHeader The authorization header containing the token.
     * @param session The HTTP session.
     * @param orderId The ID of the order to be updated.
     * @param status The new status of the order.
     * @return A CompletableFuture of ResponseEntity containing the result of the operation.
     */
    @Async("taskExecutor")
    @PostMapping("/api/order/update/Status")
    public CompletableFuture<ResponseEntity<String>> updateOrderStatus(
            @RequestHeader("Authorization") String authorizationHeader, 
            HttpSession session, 
            @RequestParam Long orderId, 
            @RequestParam String status) {
        try {
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                return CompletableFuture.completedFuture(new ResponseEntity<>(AUTH_REQUIRED_MESSAGE, HttpStatus.FORBIDDEN));
            }
            String token = authorizationHeader.replace(BEARER_PREFIX, "");
            ResponseEntity<String> response = orderService.updateOrderStatus(session, token, orderId, status);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order: " + e.getMessage()));
        }
    }
  
    /**
     * Retrieves order information by order ID asynchronously.
     * 
     * @param authorizationHeader The authorization header containing the token.
     * @param session The HTTP session.
     * @param orderId The ID of the order to retrieve information for.
     * @return A CompletableFuture of ResponseEntity containing the order information.
     */
    @Async("taskExecutor")
    @PostMapping("/api/order/get")
    public CompletableFuture<ResponseEntity<String>> getOrderInfoById(
            @RequestHeader("Authorization") String authorizationHeader, 
            HttpSession session, 
            @RequestParam Long orderId) {
        try {
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                return CompletableFuture.completedFuture(new ResponseEntity<>(AUTH_REQUIRED_MESSAGE, HttpStatus.FORBIDDEN));
            }
            String token = authorizationHeader.replace(BEARER_PREFIX, "");
            ResponseEntity<String> response = orderService.getOrderInfoById(session, token, orderId);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order: " + e.getMessage()));
        }
    }


}