
package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.services.AccountUnlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountUnlockController {

    @Autowired
    private AccountUnlockService accountUnlockService;

    @GetMapping("/account-unlock/{userEmail}")
    public String unlockAccount(@PathVariable String userEmail){
        String message = accountUnlockService.unlockAccount(userEmail);
        return message;
    }

    @GetMapping("/do-unlock")
    public String unlockAccountSuccess(@RequestParam("username") String userEmail)
    {
        String message = accountUnlockService.unlockAccountSuccess(userEmail);
        return message;
    }
}
