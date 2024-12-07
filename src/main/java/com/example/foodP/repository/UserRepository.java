package com.example.foodP.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodP.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUsername(String username); 
}
    

