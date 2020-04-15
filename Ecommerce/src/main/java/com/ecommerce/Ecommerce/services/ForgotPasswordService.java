package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.ForgotPasswordDto;

import com.ecommerce.Ecommerce.entities.Registration_Details.EmailSenderService;
import com.ecommerce.Ecommerce.entities.Registration_Details.ResetPasswordToken;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import com.ecommerce.Ecommerce.exception.TokenExpiredException;
import com.ecommerce.Ecommerce.repos.ResetPasswordRepository;
import com.ecommerce.Ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class ForgotPasswordService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private EmailSenderService emailService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
     public String resetUserPassword(String email)
     {
         Optional<User> byId=userRepository.findByEmail(email);
         User user;
         user=byId.get();
         if(user==null)
             throw new UsernameNotFoundException("User not found");

         ResetPasswordToken resetPasswordToken;
         resetPasswordToken=resetPasswordRepository.findByUser(user);
         if(resetPasswordToken!=null)
         {
             resetPasswordToken.calculationToken();
             resetPasswordRepository.save(resetPasswordToken);
         }
         else
         {
             resetPasswordToken=new ResetPasswordToken(user);
             resetPasswordRepository.save(resetPasswordToken);
         }
         SimpleMailMessage mailMessage=new SimpleMailMessage();
         mailMessage.setTo(user.getEmail());
         mailMessage.setSubject("Account activated");
         mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
         mailMessage.setText("To reset your password , please click here "
                         +"http://localhost:8080/reset-password?token="+resetPasswordToken.getToken());
                 emailService.sendEmail(mailMessage);

         return "A link has been sent to your email for password reset.";
     }

    public String updatePassword(String resetToken, ForgotPasswordDto forgotPasswordDto){
        ResetPasswordToken resetPasswordToken= resetPasswordRepository.findByToken(resetToken);
        Date presentDate = new Date();
        if (resetPasswordToken.getExpiryDate().getTime() - presentDate.getTime() <= 0){
            throw new TokenExpiredException("Token has been expired, request for new Token via Forgot Password Link");
        }
        else {
           Optional <User> byId = userRepository.findByEmail(resetPasswordToken.getUser().getEmail());

            User user;
            user=byId.get();
            String pass = forgotPasswordDto.getPassword();
            user.setPassword(passwordEncoder.encode(pass));
            userRepository.save(user);
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());

            mailMessage.setSubject("Password Updated !!");
            mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
            mailMessage.setText("Your password has been changed successfully!!");
            emailService.sendEmail(mailMessage);
            return "Password updated successfully!!!";
        }
    }
}
