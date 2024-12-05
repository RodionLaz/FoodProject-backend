package com.example.foodP.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.foodP.service.OrderService;
import com.example.foodP.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/api/order/create")
    public ResponseEntity<String> createOrder(@RequestHeader("Authorization") String authorizationHeader,HttpSession session, @RequestParam  Long itemId ){
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
}

    @PostMapping("/api/order/update")
    public ResponseEntity<String> updateOrder(@RequestHeader("Authorization") String authorizationHeader,HttpSession session,@RequestParam Long orderId) {
        try{
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                return new ResponseEntity<>("Authorization is required", HttpStatus.FORBIDDEN);
                }
                String token = authorizationHeader.replace("Bearer ", "");
                
              return orderService.updateOrder(session, token, orderId);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create order: " + e.getMessage());
        }

    }

}