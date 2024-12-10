package com.example.foodP.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.foodP.model.UserModel;
import com.example.foodP.repository.UserRepository;
import com.example.foodP.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {
    
    UserRepository userRepository;


    @Autowired
    public UserService( UserRepository userRepository){
        this.userRepository =  userRepository;
    }

    @Cacheable(value = "users", key = "#authorizationToken")
    public ResponseEntity<String> getUserByToken(String authorizationToken) {
        try {
            Long userId = JwtUtil.extractUserId(authorizationToken);
            if (userId == null) {
                return ResponseEntity.status(400).body("Invalid token.");
            }
            Optional<UserModel> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.status(404).body("User not found. Please verify the provided token and try again.");
            }
            return ResponseEntity.status(200).body(new ObjectMapper().writeValueAsString(user.get()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body("Error processing user data.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
    @CachePut(value = "users", key = "#authorizationToken")
    public ResponseEntity<String> updateUserByToken(String authorizationToken, UserModel updatedUser){
        try {
            Long userId = JwtUtil.extractUserId(authorizationToken);
            if (userId == null) {
                return ResponseEntity.status(400).body("Invalid token.");
            }
            Optional<UserModel> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.status(404).body("User not found. Please verify the provided token and try again.");
            }
            if (!updatedUser.getId().equals(user.get().getId())) {
                return ResponseEntity.status(403).body("Unauthorized.");
            }
            userRepository.save(updatedUser);
            return ResponseEntity.status(200).body(new ObjectMapper().writeValueAsString(user.get()));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body("Error processing user data.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

}
