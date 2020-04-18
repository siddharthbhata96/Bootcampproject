package com.ecommerce.Ecommerce.exception;

import com.ecommerce.Ecommerce.entities.registration.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class LockedException extends RuntimeException {

    @Autowired
    EmailSenderService emailService;

    public LockedException(String message){
        super(message);
    }

}
