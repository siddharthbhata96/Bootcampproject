package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.AddressDto;
import com.ecommerce.Ecommerce.dto.CustomerRegisterDto;
import com.ecommerce.Ecommerce.services.CustomerDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    /*@GetMapping("customer/showAddress/{id}")
     public List<Object[]> findAllProducts(@PathVariable Integer id)
    {
        return data.findAllAddress(id);
    }*/

    @PatchMapping ("/updateCustomerProfile/{id}")
    public String updateCustomerDetails(@Valid @RequestBody CustomerRegisterDto customer, @PathVariable(value = "id") Integer id, HttpServletResponse response){
        String message = data.updateCustomer(customer,id);
        if (!message.equals("User updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
    @PostMapping("/addCustomerAddress/{id}")
    public String addCustomerAddress(@Valid @RequestBody AddressDto addressDto,@PathVariable(value = "id") Integer id, HttpServletResponse response)
    {
        String message = data.addAddress(addressDto,id);
        if (!message.equals("Address added")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping("/deleteCustomerAddress/{id}")
    public String deleteCustomerAddress(@PathVariable(value = "id") Integer id, HttpServletResponse response)
    {
        String message = data.deleteAddress(id);
        if (!message.equals("Address deleted")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping("/updateCustomerAddress/{id}")
    public String updateCustomerAddress(@Valid @RequestBody AddressDto addressDto,@PathVariable(value = "id") Integer id, HttpServletResponse response)
    {
        String message = data.updateAddress(addressDto,id);
        if (!message.equals("Address updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

}
