package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.AddressRegisterDto;
import com.ecommerce.Ecommerce.dto.AddressUpdateDto;
import com.ecommerce.Ecommerce.dto.CustomerUpdateDto;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.services.CustomerDaoService;
import com.ecommerce.Ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class CustomerController {
    @Autowired
    CustomerDaoService data;

    @Autowired
    UserDaoService userDaoService;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/customer/home")
    public String customerHome(){
        Customer customer = userDaoService.getLoggedInCustomer();
        String name = customer.getFirst_name();
        return messageSource.getMessage("welcome.message",new Object[]{name}, LocaleContextHolder.getLocale());
    }

   @GetMapping("/customer/showData")
    public MappingJacksonValue getProfile()
    {
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        return data.showCustomerData(customer_user_id);
    }

   @GetMapping("customer/showAddress")
    public MappingJacksonValue getAddress()
    {
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        return data.showAddressData(customer_user_id);
    }

    /*@GetMapping("customer/showAddress/{id}")
     public List<Object[]> findAllProducts(@PathVariable Integer id)
    {
        return data.findAllAddress(id);
    }*/

    @PatchMapping ("/updateCustomerProfile")
    public String updateCustomerDetails(@RequestBody CustomerUpdateDto customer, HttpServletResponse response){
        Customer customer1=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer1.getId();
        String message = data.updateCustomer(customer,customer_user_id);
        if (!message.equals("User updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
    @PostMapping("/addCustomerAddress")
    public String addCustomerAddress(@RequestBody AddressRegisterDto addressRegisterDto, HttpServletResponse response)
    {
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        String message = data.addAddress(addressRegisterDto,customer_user_id);
        if (!message.equals("Address added")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping("/deleteCustomerAddress/{addressId}")
    public String deleteCustomerAddress(@PathVariable(value = "addressId") Integer addressId,HttpServletResponse response)
    {
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        String message = data.deleteAddress(addressId,customer_user_id);
        if (!message.equals("Address deleted")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping("/updateCustomerAddress/{addressId}")
    public String updateCustomerAddress(@RequestBody AddressUpdateDto addressUpdateDto, @PathVariable(value = "addressId") Integer addressId, HttpServletResponse response)
    {
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        String message = data.updateAddress(addressUpdateDto,addressId,customer_user_id);
        if (!message.equals("Address updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

    @PostMapping("/updateCustomerPassword")
    public String updateCustomerPasswordRegister(@RequestBody CustomerUpdateDto customerUpdateDto, HttpServletResponse response)
    {
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id=customer.getId();
        String message=data.updateCustomerPassword(customerUpdateDto,customer_user_id);
        if (!message.equals("Password updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

}
