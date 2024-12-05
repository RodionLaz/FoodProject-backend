package com.example.foodP.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.foodP.model.RestaurantModel;

public interface RestaurantRepository extends JpaRepository<RestaurantModel,Long> {

    Optional<RestaurantModel> findByEmail(String email);
    Optional<RestaurantModel> findByUsername(String username); 

    @Query("SELECT r FROM RestaurantModel r JOIN r.items i WHERE i.id = :itemId")
    Optional<RestaurantModel> findRestaurantByItemId(@Param("itemId") Long itemId);
}
