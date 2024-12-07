package com.example.foodP.controller;


import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodP.service.AuthService;

import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {
    
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @Async("taskExecutor")
    @PostMapping("/api/user/auth/login")
    public CompletableFuture<ResponseEntity<String>> login(HttpSession session,@RequestParam String username,@RequestParam String password){
        try{
            return CompletableFuture.completedFuture(authService.loginUser(username, password, session));
        }catch (Exception e){
            return CompletableFuture.completedFuture( ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage()));
        }

    }
    @Async("taskExecutor")
    @PostMapping("/api/user/auth/signup")
    public CompletableFuture<ResponseEntity<String>> signup(HttpSession session,@RequestParam String username,@RequestParam String email,@RequestParam String password){
           try {
            return CompletableFuture.completedFuture(authService.registerUser(username, password,email,session));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage()));
        }
    }
    @Async("taskExecutor")
    @PostMapping("/api/business/auth/login")
    public CompletableFuture<ResponseEntity<String>> businessLogin(HttpSession session,@RequestParam String username,@RequestParam String password){
        try{
            return CompletableFuture.completedFuture(authService.loginUser(username, password, session));
        }catch (Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage()));
        }

    }
    @Async("taskExecutor")
    @PostMapping("/api/business/auth/signup")
    public CompletableFuture<ResponseEntity<String>> businessSignup(HttpSession session,@RequestParam String username,@RequestParam String email,@RequestParam String password){
           try {
            return CompletableFuture.completedFuture(authService.registerUser(username, password,email,session));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage()));
        }
    }
}
