package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.registration.EmailSenderService;
import com.ecommerce.Ecommerce.entities.registration.User;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountUnlockService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public String unlockAccount(String useremail) {

        Optional<User> byEmail = userRepository.findByEmail(useremail);
        User user = byEmail.get();
        if (user == null)
            throw new UserNotFoundException("User not found !!");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Unlock Account");
        mailMessage.setText("To unlock your account, please click here : "
                + "http://localhost:8080/do-unlock?username=" + useremail);
        emailSenderService.sendEmail(mailMessage);

        return "Mail has been sent to you. Click on link to unlock your account";
    }

    @Transactional
    public String unlockAccountSuccess(String useremail) {

        Optional<User> byEmail = userRepository.findByEmail(useremail);
        User user = byEmail.get();
        if (user == null)
            throw new UserNotFoundException("User not found !!");

        user.setAttempts(0);
        user.setIs_nonLocked(true);
        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Unlock Successfull");
        mailMessage.setText("Account has been unlocked");
        emailSenderService.sendEmail(mailMessage);

        return "Your account is unlocked now";
    }
}