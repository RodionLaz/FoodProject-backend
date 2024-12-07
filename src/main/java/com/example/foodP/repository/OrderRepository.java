package com.example.foodP.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.foodP.model.OrderModel;

public interface OrderRepository extends JpaRepository<OrderModel,Long> {
    

    
}
