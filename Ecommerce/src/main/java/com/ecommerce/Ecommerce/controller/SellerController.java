package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.AddressDto;
import com.ecommerce.Ecommerce.dto.CustomerRegisterDto;
import com.ecommerce.Ecommerce.dto.SellerRegisterDto;
import com.ecommerce.Ecommerce.services.CustomerDaoService;
import com.ecommerce.Ecommerce.services.SellerDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class SellerController {
    @Autowired
    SellerDaoService data;

    @GetMapping("/seller/showData/{id}")
    public MappingJacksonValue getProfile(@PathVariable Integer id)
    {
        return data.showSellerData(id);
    }

    @PatchMapping("/updateSellerProfile/{id}")
    public String updateSellerDetails(@Valid @RequestBody SellerRegisterDto seller, @PathVariable(value = "id") Integer id, HttpServletResponse response){
        String message = data.updateSeller(seller,id);
        if (!message.equals("User updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
    @PostMapping("/updateSellerAddress/{id}")
    public String addCustomerAddress(@Valid @RequestBody AddressDto addressDto, @PathVariable(value = "id") Integer id, HttpServletResponse response)
    {
        String message = data.updateSellerAddress(addressDto,id);
        if (!message.equals("Address added")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
}
