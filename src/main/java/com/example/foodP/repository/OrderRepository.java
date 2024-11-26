package com.example.foodP.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodP.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel,Long> {
    

    
}
