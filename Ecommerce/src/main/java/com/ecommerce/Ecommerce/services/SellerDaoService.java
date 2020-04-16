package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.AddressDto;
import com.ecommerce.Ecommerce.dto.CustomerRegisterDto;
import com.ecommerce.Ecommerce.dto.SellerRegisterDto;
import com.ecommerce.Ecommerce.entities.Registration_Details.*;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.AddressRepository;
import com.ecommerce.Ecommerce.repos.CustomerRepository;
import com.ecommerce.Ecommerce.repos.SellerRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SellerDaoService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    AddressRepository addressRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private EmailSenderService emailService;

    public MappingJacksonValue showSellerData(Integer id)
    {
        Optional<Seller> seller=sellerRepository.findById(id);
        if(seller.isPresent()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "first_name", "last_name", "contact", "is_active","gst","company_contact","company_name");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter);

            MappingJacksonValue mapping = new MappingJacksonValue(seller);
            mapping.setFilters(filterProvider);
            return mapping;
        }
        else
        {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    @Modifying
    public String updateSeller(SellerRegisterDto sellerRegisterDto, Integer id){
        Optional<Seller> byId = sellerRepository.findById(id);
        Seller seller=byId.get();
        String a=seller.getPassword();
        BeanUtils.copyProperties(sellerRegisterDto,seller);
        if(seller != null) {
            sellerRegisterDto.getFirstName();
            sellerRegisterDto.getMiddleName();
            sellerRegisterDto.getLastName();
            String b=sellerRegisterDto.getPassword();
            sellerRegisterDto.getConfirmPassword();
            seller.setPassword(passwordEncoder.encode(sellerRegisterDto.getPassword()));
            sellerRegisterDto.getContact();
            sellerRegisterDto.getEmail();
            if(a!=b)
            {
                SimpleMailMessage mailMessage=new SimpleMailMessage();
                mailMessage.setTo(sellerRegisterDto.getEmail());
                mailMessage.setSubject("Password changed");
                mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
                mailMessage.setText("Your password was updated");
                emailService.sendEmail(mailMessage);
            }
            sellerRepository.save(seller);
            return "User updated";
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    @Modifying
    public  String updateSellerAddress(AddressDto addressDto ,Integer id)
    {
        Optional<Address> byId=addressRepository.findById(id);
        Address address=byId.get();
        BeanUtils.copyProperties(addressDto,address);
        if(address!=null) {
            addressDto.getHouse_number();
            addressDto.getCity();
            addressDto.getState();
            addressDto.getCountry();
            addressDto.getZip_code();
            addressDto.getLabel();
            addressRepository.save(address);
            return "Address saved";
        }
        else{
            return "Wrong address id";
        }
    }
}

