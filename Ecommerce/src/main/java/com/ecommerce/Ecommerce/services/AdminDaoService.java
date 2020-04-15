package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.Registration_Details.*;
import com.ecommerce.Ecommerce.repos.CustomerRepository;
import com.ecommerce.Ecommerce.repos.SellerRepository;
import com.ecommerce.Ecommerce.repos.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


@Service
public class AdminDaoService{

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    public MappingJacksonValue listAllCustomer(String page, String size, String SortBy)
    {
        List<Customer> customers = customerRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size), Sort.by(SortBy))).getContent();

        SimpleBeanPropertyFilter filter1=SimpleBeanPropertyFilter.filterOutAllExcept("id","first_name","middle_name","last_name","email","is_active");

        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter",filter1);

        MappingJacksonValue mapping=new MappingJacksonValue(customers);
        mapping.setFilters(filterProvider);
        return mapping;
    }

    public MappingJacksonValue listAllSeller(String page,String size,String SortBy)
    {
        List<Seller>  sellers=sellerRepository.findAll(PageRequest.of(Integer.parseInt(page),Integer.parseInt(size),Sort.by(SortBy))).getContent();
        SimpleBeanPropertyFilter filter2=SimpleBeanPropertyFilter.filterOutAllExcept("id","first_name","middle_name","last_name","email","is_active","company_name","company_contact");

        FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter",filter2);

        MappingJacksonValue mapping=new MappingJacksonValue(sellers);
        mapping.setFilters(filterProvider);

        return mapping;
    }

    @Transactional
    public String activateUser(Integer id)
    {
        Optional<User> byId = userRepository.findById(id);
        User user;
        user=byId.get();
        if(user.isIs_active()==false)
        {
            user.setIs_active(true);
            userRepository.save(user);
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Account activated");
            mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
            mailMessage.setText("Your account is activated");
            emailSenderService.sendEmail((mailMessage));
            return "User is Activated";
        }
        else
        {
            return "User is already activated";
        }
    }

    @Transactional
    public String deactivateUser(Integer id)
    {
        Optional<User> byId = userRepository.findById(id);
        User user;
        user=byId.get();
        if(user.isIs_active()==true)
        {
            user.setIs_active(false);
            userRepository.save(user);
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Account deactivated");
            mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
            mailMessage.setText("Your account is deactivated");
            emailSenderService.sendEmail((mailMessage));
            return "User is Deactivated";
        }
        else
        {
            return "User is already deactivated";
        }
    }
}
