package com.example.foodP.controller;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.foodP.model.UserModel;
import com.example.foodP.service.UserService;

@RestController
public class UserController {
    private static final String AUTH_REQUIRED_MESSAGE = "Authorization is required";

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @Async("taskExecutor")
    @GetMapping("/api/user/get")
    public CompletableFuture<ResponseEntity<String>> getUser(@RequestHeader("Authorization") String authorizationHeader){
        try {
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                return CompletableFuture.completedFuture(new ResponseEntity<>(AUTH_REQUIRED_MESSAGE, HttpStatus.FORBIDDEN));
            }
            return CompletableFuture.completedFuture(userService.getUserByToken(authorizationHeader));
        } catch (Exception e) {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get order: " + e.getMessage())); 
        }
    }

    @Async("taskExecutor")
    @PostMapping("/api/user/update")
    public CompletableFuture<ResponseEntity<String>> updateUser(@RequestHeader("Authorization") String authorizationHeader, @RequestBody @Valid UserModel userModel){
        try {
            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
                return CompletableFuture.completedFuture(new ResponseEntity<>(AUTH_REQUIRED_MESSAGE, HttpStatus.FORBIDDEN));
            }
            return CompletableFuture.completedFuture(userService.updateUserByToken(authorizationHeader,userModel));
        }catch(MethodArgumentNotValidException e){
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage())); 
        }catch(Exception e){
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get order: " + e.getMessage())); 
        }
    }


}
