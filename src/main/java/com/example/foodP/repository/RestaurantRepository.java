package com.example.foodP.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodP.model.RestaurantModel;

public interface RestaurantRepository extends JpaRepository<RestaurantModel,Long> {
    Optional<RestaurantModel> findByEmail(String email);
}
