/*
package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.Registration_Details.Customer;
import com.ecommerce.Ecommerce.entities.Registration_Details.Seller;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.AddressRepository;
import com.ecommerce.Ecommerce.repos.CustomerRepository;
import com.ecommerce.Ecommerce.repos.SellerRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerDaoService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    AddressRepository addressRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public MappingJacksonValue showCustomerData(Integer id)
    {
        Optional<Seller> seller=sellerRepository.findById(id);
        if(seller.isPresent()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "first_name", "last_name", "contact", "is_active");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter);

            MappingJacksonValue mapping = new MappingJacksonValue(customer);
            mapping.setFilters(filterProvider);
            return mapping;
        }
        else
        {
            throw new UserNotFoundException("User not found");
        }
    }
}
*/
