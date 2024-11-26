package com.example.foodP.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class RestaurantModel extends UserModel  {

    @Column(nullable = false, unique = true)
    private String restaurantName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, unique = true)
    private int phoneNumber;

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();


    public void addItem(Item item) {
        items.add(item);
        item.setRestaurant(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setRestaurant(null);
    }

    // Getters and Setters for items
    public List<Item> getItems() {
        return items;
    }
    
    public RestaurantModel() {
        super();
    }

    protected RestaurantModel(RestaurantBuilder builder) {
        super(builder.username, builder.email, builder.password);
        this.restaurantName = builder.name;
        this.address = builder.address;
        this.phoneNumber = builder.phoneNumber;
        this.accountType = builder.accountType;
    }


    
    public static class RestaurantBuilder {
        private String name;
        private String address;
        private String email;
        private int phoneNumber;
        private String username;
        private String password;
        private AccountType accountType;

        public RestaurantBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public RestaurantBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public RestaurantBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public RestaurantBuilder setPhoneNumber(int phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public RestaurantBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public RestaurantBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public RestaurantBuilder setAccountType(AccountType accountType) {
            this.accountType = accountType;
            return this;
        }

        public RestaurantModel build() {
            return new RestaurantModel(this);
        }
    }
    
}

