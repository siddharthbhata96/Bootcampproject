package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entities.order.Cart;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.User;
import com.ecommerce.Ecommerce.repos.UserRepository;
import com.ecommerce.Ecommerce.services.CartDaoService;
import com.ecommerce.Ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    UserDaoService userDaoService;

    @Autowired
    CartDaoService data;

    @PostMapping("customer/addToCart/{productVariation_id}")
    public String addToCart(@RequestBody Cart cart, @PathVariable Integer productVariation_id){
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        String cart1= data.addToCart(customer_user_id, cart, productVariation_id);
        return  cart1; }
    }

