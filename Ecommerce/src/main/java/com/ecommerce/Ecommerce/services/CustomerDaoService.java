package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.Registration_Details.Address;
import com.ecommerce.Ecommerce.entities.Registration_Details.Customer;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.AddressRepository;
import com.ecommerce.Ecommerce.repos.CustomerRepository;
import com.ecommerce.Ecommerce.repos.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDaoService {
    @Autowired
    UserRepository userRepository;

   @Autowired
    CustomerRepository customerRepository;

   @Autowired
    AddressRepository addressRepository;

   public MappingJacksonValue showCustomerData(String emailid)
    {
        Optional<Customer> customer=customerRepository.findByEmail(emailid);
        if(customer.isPresent()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "first_name", "middle_name", "last_name", "contact", "is_active");

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

   public  MappingJacksonValue showAddressData(Integer id)
    {
        Optional<Customer>customer=customerRepository.findById(id);
        if(customer.isPresent()){
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("address");
            FilterProvider filterProvider=new SimpleFilterProvider().addFilter("userFilter",filter);

            MappingJacksonValue mapping=new MappingJacksonValue(customer);
            mapping.setFilters(filterProvider);
            return mapping;
        }
        else
        {
            throw new UsernameNotFoundException("No customer by this id found");
        }
    }
 /* public List<Object[]> findAllAddress(Integer id){
      return addressRepository.findCustomer(id);
  }*/

}
