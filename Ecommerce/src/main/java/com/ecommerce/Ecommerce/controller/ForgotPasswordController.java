package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.dto.ForgotPasswordDto;
import com.ecommerce.Ecommerce.services.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ForgotPasswordController {
    @Autowired
    ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public String resetPassword(@RequestBody String email){
        String message = forgotPasswordService.resetUserPassword(email);
       return message;
        //return "hello";
    }

    @PutMapping("/reset-password")
    public String setPassword(@RequestParam("token") String resetToken, @RequestBody ForgotPasswordDto forgotPasswordDto){
        String message =forgotPasswordService.updatePassword(resetToken,forgotPasswordDto);
        return message;
    }
}
