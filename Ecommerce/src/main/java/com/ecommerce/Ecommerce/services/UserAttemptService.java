/*
package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.security.AppUser;
import com.ecommerce.Ecommerce.entities.Registration_Details.EmailSenderService;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import com.ecommerce.Ecommerce.entities.Registration_Details.UserAttempts;
import com.ecommerce.Ecommerce.repos.UserAttemptsRepo;
import com.ecommerce.Ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class UserAttemptService {
    public static final int ATTEMPT= 2;

    @Autowired
    private UserAttemptsRepo userAttemptsRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    AppUser appUser;

    @Transactional
    public void userFailAttempt(String email)
    {
        UserAttempts userAttempts=userAttemptsRepo.findByEmail(email);
        Optional<User> byId=userRepository.findByEmail(email);
        User user;
        user=byId.get();
        if(null!=user){
            if(null!=userAttempts)
            {
                int attempt=userAttempts.getAttempts();
                if(attempt>=ATTEMPT)
                {
                    userAttempts.setAttempts(attempt+1);
                    userAttempts.setLastModified(new Date());
                    userAttemptsRepo.save(userAttempts);

                }
            }
        }
    }
}
*/
