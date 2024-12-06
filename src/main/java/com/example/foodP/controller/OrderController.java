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

import com.example.foodP.service.OrderService;
import com.example.foodP.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

public class OrderController {
    
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

        @Async("taskExecutor")
        @PostMapping("/api/order/create")
        public CompletableFuture<ResponseEntity<String>> createOrder(
        @RequestHeader("Authorization") String authorizationHeader,
        HttpSession session,
        @RequestParam  Long itemId ){
            return CompletableFuture.supplyAsync(()-> {
            try{
                if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                    return new ResponseEntity<>("Authorization is required", HttpStatus.FORBIDDEN);
                    }
                    if (itemId <= 0) {
                        return new ResponseEntity<>("Invalid item ID", HttpStatus.BAD_REQUEST);
                    }
                    String token = authorizationHeader.replace("Bearer ", "");
                    
                    return orderService.createOrder(session, itemId,token,new Date(System.currentTimeMillis()));
            }catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order: " + e.getMessage());
            }
        });
    }

    @Async("taskExecutor")
    @PostMapping("/api/order/update/Status")
    public CompletableFuture<ResponseEntity<String>> updateOrderStatus(
            @RequestHeader("Authorization") String authorizationHeader, 
            HttpSession session, 
            @RequestParam Long orderId, 
            @RequestParam String status) {
        try {
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                return CompletableFuture.completedFuture(new ResponseEntity<>("Authorization is required", HttpStatus.FORBIDDEN));
            }
            String token = authorizationHeader.replace("Bearer ", "");
            ResponseEntity<String> response = orderService.updateOrderStatus(session, token, orderId, status);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order: " + e.getMessage()));
        }
    }


}