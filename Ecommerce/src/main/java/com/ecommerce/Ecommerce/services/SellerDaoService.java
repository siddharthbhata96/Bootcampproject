package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.*;
import com.ecommerce.Ecommerce.entities.registration.*;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.AddressRepository;
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
    public String updateSeller(SellerUpdateDto sellerUpdateDto, Integer id){
        Optional<Seller> seller = sellerRepository.findById(id);
        if (seller.isPresent()){
            Seller seller1= seller.get();
            if(sellerUpdateDto.getFirstName() != null)
                seller1.setFirst_name(sellerUpdateDto.getFirstName());

            if(sellerUpdateDto.getMiddleName() != null)
                seller1.setMiddle_name(sellerUpdateDto.getMiddleName());

            if(sellerUpdateDto.getLastName() != null)
                seller1.setLast_name(sellerUpdateDto.getLastName());

            if (sellerUpdateDto.getEmail() != null)
                seller1.setEmail(sellerUpdateDto.getEmail());

            if(sellerUpdateDto.getGst() != null)
                seller1.setGST(sellerUpdateDto.getGst());

            if (sellerUpdateDto.getCompany_name() != null)
                seller1.setCompany_name(sellerUpdateDto.getCompany_name());

            if (sellerUpdateDto.getCompany_contact() != null)
                seller1.setCompany_contact(sellerUpdateDto.getCompany_contact());

            sellerRepository.save(seller1);
            return "Profile updated successfully";
        }
        else
            throw new UserNotFoundException("User not found");
    }

    @Transactional
    @Modifying
    public  String updateSellerAddress(AddressUpdateDto addressUpdateDto, Integer userId,Integer addressId)
    {
        Optional<Address> address=addressRepository.findById(addressId);
        if(address.isPresent()) {
            Address address1 = address.get();
            if (addressUpdateDto.getHouse_number() != null) {
                address1.setHouse_number(addressUpdateDto.getHouse_number());
            }
            if (addressUpdateDto.getCity() != null) {
                address1.setCity(addressUpdateDto.getCity());
            }
            if (addressUpdateDto.getState() != null) {
                address1.setCity(addressUpdateDto.getState());
            }
            if (addressUpdateDto.getCountry() != null)
            {
                address1.setCountry(addressUpdateDto.getCountry());
            }
            if(addressUpdateDto.getZip_code()!=null)
            {
                address1.setZip_code(addressUpdateDto.getZip_code());
            }
            if(addressUpdateDto.getLabel()!=null)
            {
                address1.setLabel(addressUpdateDto.getLabel());
            }
            addressRepository.save(address1);
            return "Address saved";
        }
        else{
            return "Wrong address Id";
        }
    }

    @Transactional
    @Modifying
    public String updateSellerPassword(SellerUpdateDto sellerUpdateDto, Integer id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if (seller.isPresent()) {
            Seller seller1 = seller.get();
            String matchPassword = seller1.getPassword();
            if (sellerUpdateDto.getPassword() != null) {
                String password = passwordEncoder.encode(sellerUpdateDto.getPassword());
                if(sellerUpdateDto.getPassword().equals(sellerUpdateDto.getConfirmPassword())) {
                    if (!password.equals(matchPassword)) {
                        SimpleMailMessage mailMessage = new SimpleMailMessage();
                        mailMessage.setTo(seller1.getEmail());
                        mailMessage.setSubject("Password changed");
                        mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
                        mailMessage.setText("Your password was updated");
                        emailService.sendEmail(mailMessage);
                        seller1.setPassword(password);
                    }
                }
                else{
                    return "Password does not match";
                }
            }
        }
        return "Password Updated";
    }
}

