package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.*;
import com.ecommerce.Ecommerce.entities.registration.Seller;
import com.ecommerce.Ecommerce.services.SellerDaoService;
import com.ecommerce.Ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class SellerController {
    @Autowired
    SellerDaoService data;

    @Autowired
    UserDaoService userDaoService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/seller/home")
    public String sellerHome(){
        Seller seller = userDaoService.getLoggedInSeller();
        String name = seller.getFirst_name();
        return messageSource.getMessage("welcome.message",new Object[]{name}, LocaleContextHolder.getLocale());
    }

    @GetMapping("/seller/showData/")
    public MappingJacksonValue getProfile()
    {
        Seller seller=userDaoService.getLoggedInSeller();
        Integer seller_user_id=seller.getId();
        return data.showSellerData(seller_user_id);
    }

    @PatchMapping("/updateSellerProfile/")
    public String updateSellerDetails(@RequestBody SellerUpdateDto seller,HttpServletResponse response){
        Seller seller1=userDaoService.getLoggedInSeller();
        Integer seller_user_id=seller1.getId();
        String message = data.updateSeller(seller,seller_user_id);
        if (!message.equals("User updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }
    @PostMapping("/updateSellerAddress/{addressId}")
    public String updateCustomerAddress(@RequestBody AddressUpdateDto addressUpdateDto, @PathVariable(value = "addressId") Integer addressId, HttpServletResponse response)
    {
        Seller seller2=userDaoService.getLoggedInSeller();
        Integer seller_user_id=seller2.getId();
        String message = data.updateSellerAddress(addressUpdateDto,seller_user_id,addressId);
        if (!message.equals("Address updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }


    @PostMapping("/updateSellerPassword/")
    public String updateSellerPasswordRegister(@RequestBody SellerUpdateDto sellerUpdateDto, HttpServletResponse response)
    {
        Seller seller3=userDaoService.getLoggedInSeller();
        Integer seller_user_id=seller3.getId();
        String message=data.updateSellerPassword(sellerUpdateDto,seller_user_id);
        if (!message.equals("Password updated")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return message;
    }

}
