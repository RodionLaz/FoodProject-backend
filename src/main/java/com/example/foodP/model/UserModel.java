package com.example.foodP.model;

import jakarta.persistence.*;
import javax.validation.constraints.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name ="users")
public class UserModel {


    @NotNull(message = "ID cannot be null")
    @Size(min = 1, message = "ID cannot be empty")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email format")
    @Size(min = 1, message = "Email cannot be empty")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Username cannot be null")
    @Size(min = 3, message = "Username cannot be less then 3 chars")
    @Column(nullable = false)
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password cannot be less then 8 chars")
    @Column(nullable = false)
    private String password;

    public UserModel() {}

    public UserModel(String username,String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel orElseThrow(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'orElseThrow'");
    }
}
