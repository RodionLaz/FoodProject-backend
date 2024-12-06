package com.example.foodP.model;

public enum OrderStatusType {
    CREATED, RECEIVED, ACCEPTED, DELIVERING, DELIVERED;

   
    public static boolean isValidStatus(String status) {
        for (OrderStatusType type : OrderStatusType.values()) {
            if (type.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
    public static OrderStatusType stringToEnum(String status) {
        for (OrderStatusType type : OrderStatusType.values()) {
            if (type.name().equalsIgnoreCase(status)) {
                return type;
            }
        }
        return null;
    } 
}
