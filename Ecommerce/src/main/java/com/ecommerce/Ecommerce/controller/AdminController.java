package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.services.AdminDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AdminController {
    @Autowired
    AdminDaoService data;

    //displaying all customers
    @GetMapping("/admin/listCustomers")
    public MappingJacksonValue getCustomers(@RequestParam(defaultValue = "0") String page, @RequestParam(defaultValue = "10")String size, @RequestParam(defaultValue = "id") String SortBy){
        return data.listAllCustomer(page, size, SortBy);
    }

    //displaying all sellers
    @GetMapping("/admin/listSellers")
    public MappingJacksonValue getSellers(@RequestParam(defaultValue = "0")String page,@RequestParam(defaultValue = "10")String size,@RequestParam(defaultValue = "id")String SortBy)
    {
        return data.listAllSeller(page, size, SortBy);
    }

    //activating user
    @PatchMapping("/admin/activateUser/{id}")
    public String activateUser(@PathVariable(value = "id") Integer id){
        String b=data.activateUser(id);
        return b;
        }

    //deactivating user
    @PatchMapping("/admin/deactivateUser/{id}")
    public String deactivateUser(@PathVariable(value = "id") Integer id){
        String b=data.deactivateUser(id);
        return b;
    }
}


