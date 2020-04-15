package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entities.Order_Details.Orders;
import com.ecommerce.Ecommerce.services.OrderDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderDaoService data;

    @PostMapping("{customer_user_id}/order/{cart_id}")
    public void addToOrder(@PathVariable Integer customer_user_id, @RequestBody Orders orders, @PathVariable Integer cart_id){
        Orders orders1= data.addToOrder(customer_user_id, orders, cart_id);
    }

}
