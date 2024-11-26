package com.example.foodP.model;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long businessId;

    @Column(nullable = false)
    private OrderStatusType status;

    @Column(nullable = false)
    private Date orderCreatedDate;

    @Column(nullable = true)
    private Date orderDoneDate;

    public OrderModel() {}

    public OrderModel(Long customerId, Long businessId, OrderStatusType status,Date orderCreatedDate ) {
        this.customerId = customerId;
        this.businessId = businessId;
        this.status = status;
        this.orderCreatedDate = orderCreatedDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    public Date getOrderCreatedDate() {
        return orderCreatedDate;
    }

    public void setOrderCreatedDate(Date orderCreatedDate) {
        this.orderCreatedDate = orderCreatedDate;
    }

    public Date getOrderDoneDate() {
        return orderDoneDate;
    }

    public void setOrderDoneDate(Date orderDoneDate) {
        this.orderDoneDate = orderDoneDate;
    }
}
