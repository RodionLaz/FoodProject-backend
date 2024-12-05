package com.example.foodP.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.foodP.model.AccountType;
import com.example.foodP.model.RestaurantModel;
import com.example.foodP.model.RestaurantModel.RestaurantBuilder;
import com.example.foodP.model.UserModel;
import com.example.foodP.repository.RestaurantRepository;
import com.example.foodP.repository.UserRepository;
import com.example.foodP.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {
    

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.restaurantRepository = restaurantRepository;
    }


    
    public ResponseEntity<String> registerUser(String username,String password, String email, HttpSession session){
        Optional<UserModel> existingUser = userRepository.findByEmail(email);
        Optional<RestaurantModel> existingRestaurant = restaurantRepository.findByEmail(email);
        if (existingUser.isPresent() ||existingRestaurant.isPresent() ) {
            return new ResponseEntity<>("User with email " + existingUser.get().getEmail() + " already exists.", HttpStatus.BAD_REQUEST);
        }

        UserModel newUser = new UserModel(username,email,passwordEncoder.encode(password));
        userRepository.save(newUser);
        String jwt = JwtUtil.generateToken(username, newUser.getEmail(),existingUser.get().getId());
        session.setAttribute("jwt", jwt);
        return new ResponseEntity<>("User Created",HttpStatus.CREATED);
    }
  

    public ResponseEntity<String> loginUser(String username,String password, HttpSession session){
        Optional<UserModel> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>("User doesn't exists.", HttpStatus.BAD_REQUEST);
        }
        if (passwordEncoder.matches(password, existingUser.get().getPassword())) {
            String jwt = JwtUtil.generateToken(username, existingUser.get().getEmail(),existingUser.get().getId());
            session.setAttribute("jwt", jwt);
            return new ResponseEntity<>("Login successful.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid credentials.", HttpStatus.UNAUTHORIZED);
        }
    }


    public ResponseEntity<String> registerBusiness(String username,String password, String email,String RestaurantName, String address,String accountType,int phoneNumber, HttpSession session){
        Optional<UserModel> existingUser = userRepository.findByEmail(email);
        Optional<RestaurantModel> existingRestaurant = restaurantRepository.findByEmail(email);
        if (existingUser.isPresent() ||existingRestaurant.isPresent() ) {
            return new ResponseEntity<>("User with email " + existingUser.get().getEmail() + " already exists.", HttpStatus.BAD_REQUEST);
        }
        AccountType accountTypeEnum = AccountType.ORDER_RECEIVER;
        switch (accountType) {
            case "restaurant_manager":
                accountTypeEnum = AccountType.RESTAURANT_MANAGER;
                break;
            case "order_receiver":
                accountTypeEnum = AccountType.RESTAURANT_MANAGER;
                break;
            default:
                break;
        }
        RestaurantModel newRestaurant = new RestaurantBuilder()
            .setAccountType(accountTypeEnum)
            .setAddress(address)
            .setEmail(email)
            .setPhoneNumber(phoneNumber)
            .setName(RestaurantName)
            .setPassword(passwordEncoder.encode(password))
            .setUsername(username)
            .build();
            
        restaurantRepository.save(newRestaurant);   
        String jwt = JwtUtil.generateToken(username, newRestaurant.getEmail(),existingUser.get().getId());
        session.setAttribute("jwt", jwt);
        return new ResponseEntity<>("business Created",HttpStatus.CREATED);
    }
  

    public ResponseEntity<String> loginBusiness(String username,String password, HttpSession session){
        Optional<RestaurantModel> existingUser = restaurantRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            return new ResponseEntity<>("User doesn't exists.", HttpStatus.BAD_REQUEST);
        }
        if (passwordEncoder.matches(password, existingUser.get().getPassword())) {
            String jwt = JwtUtil.generateToken(username,existingUser.get().getId());
            session.setAttribute("jwt", jwt);
            return new ResponseEntity<>("Login successful.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Invalid credentials.", HttpStatus.UNAUTHORIZED);
        }
    }

}
