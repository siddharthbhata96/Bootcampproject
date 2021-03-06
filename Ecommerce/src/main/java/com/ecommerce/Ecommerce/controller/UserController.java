package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.CustomerRegisterDto;
import com.ecommerce.Ecommerce.dto.SellerRegisterDto;
import com.ecommerce.Ecommerce.entities.registration.*;
import com.ecommerce.Ecommerce.services.UserDaoService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    public String createCust(@RequestBody CustomerRegisterDto customerRegisterDto)
    {
      String b=data.createCustomer(customerRegisterDto);
      return b;
    }

    @PostMapping("/sellSave")
    public String createSell(@RequestBody SellerRegisterDto sellerRegisterDto)
    {
        String c=data.createSupplier(sellerRegisterDto);
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
