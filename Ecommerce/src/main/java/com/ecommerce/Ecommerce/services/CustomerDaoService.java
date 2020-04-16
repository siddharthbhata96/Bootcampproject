package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.AddressDto;
import com.ecommerce.Ecommerce.dto.CustomerRegisterDto;
import com.ecommerce.Ecommerce.entities.registration.Address;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.User;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.AddressRepository;
import com.ecommerce.Ecommerce.repos.CustomerRepository;
import com.ecommerce.Ecommerce.repos.UserRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CustomerDaoService {
    @Autowired
    UserRepository userRepository;

   @Autowired
    CustomerRepository customerRepository;

   @Autowired
    AddressRepository addressRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   public MappingJacksonValue showCustomerData(String emailId)
    {
        Optional<Customer> customer=customerRepository.findByEmail(emailId);
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

    @Transactional
    @Modifying
    public String updateCustomer(CustomerRegisterDto customerRegisterDto, Integer id){
        Optional<Customer> byId = customerRepository.findById(id);
        Customer customer=byId.get();
        BeanUtils.copyProperties(customerRegisterDto,customer);
        if(customer != null) {
            customerRegisterDto.getFirstName();
            customerRegisterDto.getMiddleName();
            customerRegisterDto.getLastName();
            customerRegisterDto.getPassword();
            customerRegisterDto.getConfirmPassword();
            customer.setPassword(passwordEncoder.encode(customerRegisterDto.getPassword()));
            customerRegisterDto.getContact();
            customerRegisterDto.getEmail();
            customerRepository.save(customer);
            return "User updated";
        }else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    @Modifying
    public String addAddress(AddressDto addressDto, Integer id){

        Optional<Customer> byId = customerRepository.findById(id);
        Customer customer=byId.get();
        User user=(User)customer;
        if(customer != null) {
            Address address = new Address();
            address.setUser(user);//problem
            address.setHouse_number(addressDto.getHouse_number());
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setCountry(addressDto.getCountry());
            address.setZip_code(addressDto.getZip_code());
            address.setLabel(addressDto.getLabel());
            addressRepository.save(address);

            return "Address added";

        }else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Transactional
    @Modifying
    public String deleteAddress(Integer id){

       Optional <Address> byId = addressRepository.findById(id);
        Address address=byId.get();
        if(address != null) {
            address.setDeleted(true);
            addressRepository.save(address);

            return "Address deleted";

        }else {
            throw new UserNotFoundException("Address not found");
        }
    }

    @Transactional
    @Modifying
    public  String updateAddress(AddressDto addressDto ,Integer id)
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
 /* public List<Object[]> findAllAddress(Integer id){
      return addressRepository.findCustomer(id);
  }*/




}
