package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.Product_Details.Category;
import com.ecommerce.Ecommerce.entities.Registration_Details.*;
import com.ecommerce.Ecommerce.repos.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDaoService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;


    @Autowired
    private EmailSenderService emailSenderService;

    public List<User> user = new ArrayList();
    public List<Customer> customer = new ArrayList();
    public List<Seller> seller = new ArrayList();
    public Role role = new Role();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> findAll() {
        return user;
    }

    public User save(User saving) {
        String hpass = saving.getPassword();
        saving.setPassword(passwordEncoder.encode(hpass));
        user.add(saving);
        userRepository.save(saving);
        return saving;
    }

    public String createCustomer(Customer custSaving) {
        Optional<User> existingUser = userRepository.findByEmail(custSaving.getEmail());
        if (existingUser.isPresent())
        {
            return "This email already exists";
        }
        else
            {
            String hpass = custSaving.getPassword();
            custSaving.setPassword(passwordEncoder.encode(hpass));
            customer.add(custSaving);
            Optional<Role> role1 = roleRepository.findById(2);

            role = role1.get();

            custSaving.setRoles(Arrays.asList(role));

            userRepository.save(custSaving);


            ConfirmationToken confirmationToken = new ConfirmationToken(custSaving);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(custSaving.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail((mailMessage));
            return "Registration successful";
        }
    }

    public String createSupplier(Seller sellSaving) {
        Optional<User> existingUser = userRepository.findByEmail(sellSaving.getEmail());
        if (existingUser.isPresent())
        {
            return "This email already exists";
        }
        else {
            String hpass = sellSaving.getPassword();
            sellSaving.setPassword(passwordEncoder.encode(hpass));
            seller.add(sellSaving);
            Optional<Role> role1 = roleRepository.findById(3);

            role = role1.get();

            sellSaving.setRoles(Arrays.asList(role));
            userRepository.save(sellSaving);

            ConfirmationToken confirmationToken = new ConfirmationToken(sellSaving);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(sellSaving.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail((mailMessage));
            return "Registration successful.Admin will confirm your account";
        }
    }
    public String confirmAccount(String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
            Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
            if(user.isPresent())
            {
                User userobj=user.get();
                userobj.setIs_active(true);
                userRepository.save(userobj);
            }

            return "Your account is activated" ;
        }
        else {
            return "Error ! Please try again";
        }
    }

    
    public String createAddress(Integer id, Address address) {
        List<Address> addresses = new ArrayList<>();
        User user;  //creation of variable of type user

        Optional<User> byId = userRepository.findById(id);//getting id from user table

        user = byId.get();//storing that id in user variable

        address.setUser(user);  //assignment of user_id to address table

        Address address1 = addressRepository.save(address);
        addresses.add(address1);
        user.setAddress(addresses);

        return "Address saved successfully";

    }
}