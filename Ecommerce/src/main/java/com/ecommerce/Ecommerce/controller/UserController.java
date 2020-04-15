package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entities.Product_Details.Category;
import com.ecommerce.Ecommerce.entities.Product_Details.Product;
import com.ecommerce.Ecommerce.entities.Product_Details.Product_Variation;
import com.ecommerce.Ecommerce.entities.Registration_Details.*;
import com.ecommerce.Ecommerce.services.UserDaoService;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
public class UserController {
    @Autowired
    private UserDaoService data;

    @Autowired
    private TokenStore tokenStore;

    ObjectMapper objectMapper=new ObjectMapper();//converting object mapper to class level variable

    @GetMapping("/show")
    public List<User> retrieveAllEmp()
    {
        return data.findAll();
    }

    @PostMapping("/userSave")
    public void createUser(@Valid @RequestBody User user)
    {
        User a=data.save(user);
    }

    @PostMapping("/custSave")
    public String createCust(@RequestBody Customer customer)
    {
      String b=data.createCustomer(customer);
      return b;
    }

    @PostMapping("/sellSave")
    public String createSell(@RequestBody Seller seller)
    {
        String c=data.createSupplier(seller);
        return c;
    }

    @PostMapping("/{user_id}/addressSave")
    public String createAddress(@PathVariable(value = "user_id") Integer user_id, @RequestBody Address address) {
        String d=data.createAddress(user_id, address);
        return d;
    }

    @GetMapping("/admin/home")
    public String adminHome(){
        return "Welcome Admin To Online Shopping Portal";
    }

    @GetMapping("/seller/home")
    public String sellerHome(){
        return "Welcome Seller To Online Shopping Portal";
    }

    @GetMapping("/customer/home")
    public String customerHome(){
        return "Welcome Customer To Online Shopping Portal";
    }

    @PostMapping(value="/confirmSave")
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        String message= data.confirmAccount(confirmationToken);
        return message;
    }

    @GetMapping("/doLogout")
    public String logout(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }
        return "Logged out successfully";
    }
}
