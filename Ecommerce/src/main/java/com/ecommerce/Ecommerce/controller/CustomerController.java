package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.services.CustomerDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    CustomerDaoService data;

   @GetMapping("/customer/showData/{emailid}")
    public MappingJacksonValue getProfile(@PathVariable String emailid)
    {
        return data.showCustomerData(emailid);
    }

    @GetMapping("customer/showAddress/{id}")
    public MappingJacksonValue getAddress(@PathVariable Integer id)
    {
        return data.showAddressData(id);
    }

   /* @GetMapping("customer/showAddress/{id}")
     public List<Object[]> findAllProducts(@PathVariable Integer id)
    {
        return data.findAllAddress(id);
    }*/
}
