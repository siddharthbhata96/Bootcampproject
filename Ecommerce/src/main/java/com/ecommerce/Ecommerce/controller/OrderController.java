package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entities.order.Orders;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.services.OrderDaoService;
import com.ecommerce.Ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderDaoService data;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("customer/order/{cart_id}")
    public String addToOrder( @RequestBody Orders orders, @PathVariable Integer cart_id){
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        String orders1= data.addToOrders(customer_user_id, orders, cart_id);

        return orders1;
    }

}
