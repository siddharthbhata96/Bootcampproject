package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.AddressRegisterDto;
import com.ecommerce.Ecommerce.dto.AddressUpdateDto;
import com.ecommerce.Ecommerce.dto.CustomerRegisterDto;
import com.ecommerce.Ecommerce.dto.CustomerUpdateDto;
import com.ecommerce.Ecommerce.entities.registration.Address;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.EmailSenderService;
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
import org.springframework.mail.SimpleMailMessage;
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

    @Autowired
    private EmailSenderService emailService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public MappingJacksonValue showCustomerData(Integer Id) {
        Optional<Customer> customer = customerRepository.findById(Id);
        if (customer.isPresent()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "first_name", "middle_name", "last_name", "contact", "is_active");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter);

            MappingJacksonValue mapping = new MappingJacksonValue(customer);
            mapping.setFilters(filterProvider);
            return mapping;
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    public MappingJacksonValue showAddressData(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("address");
            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", filter);
            MappingJacksonValue mapping = new MappingJacksonValue(customer);
            mapping.setFilters(filterProvider);
            return mapping;
        } else {
            throw new UsernameNotFoundException("No customer by this id found");
        }
    }

    @Transactional
    @Modifying
    public String updateCustomer(CustomerUpdateDto customerUpdateDto, Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer customer1 = customer.get();
            if (customerUpdateDto.getFirstName() != null) {
                customer1.setFirst_name(customerUpdateDto.getFirstName());
            }
            if (customerUpdateDto.getMiddleName() != null) {
                customer1.setMiddle_name(customerUpdateDto.getMiddleName());
            }
            if (customerUpdateDto.getLastName() != null) {
                customer1.setLast_name(customerUpdateDto.getLastName());
            }
            if (customerUpdateDto.getEmail() != null) {
                customer1.setEmail(customerUpdateDto.getEmail());
            }
            customerRepository.save(customer1);
            return "User updated";
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Transactional
    @Modifying
    public String updateCustomerPassword(CustomerUpdateDto customerUpdateDto, Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer customer1 = customer.get();
            String matchPassword = customer1.getPassword();
            if (customerUpdateDto.getPassword() != null) {
                String password = passwordEncoder.encode(customerUpdateDto.getPassword());
                if(customerUpdateDto.getPassword().equals(customerUpdateDto.getConfirmPassword())) {
                    if (!password.equals(matchPassword)) {
                        SimpleMailMessage mailMessage = new SimpleMailMessage();
                        mailMessage.setTo(customer1.getEmail());
                        mailMessage.setSubject("Password changed");
                        mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
                        mailMessage.setText("Your password was updated");
                        emailService.sendEmail(mailMessage);
                        customer1.setPassword(password);
                    }
                }
                else{
                    return "Password does not match";
                }
            }
        }
        return "Password Updated";
    }


    @Transactional
    @Modifying
    public String addAddress(AddressRegisterDto addressRegisterDto, Integer id){

        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()) {
            Customer customer1=customer.get();
            User user=(User)customer1;
            Address address = new Address();
            address.setUser(user);//problem
            address.setHouse_number(addressRegisterDto.getHouse_number());
            address.setCity(addressRegisterDto.getCity());
            address.setState(addressRegisterDto.getState());
            address.setCountry(addressRegisterDto.getCountry());
            address.setZip_code(addressRegisterDto.getZip_code());
            address.setLabel(addressRegisterDto.getLabel());
            addressRepository.save(address);

            return "Address added";

        }else {
            throw new UserNotFoundException("User not found");
        }
    }


    @Transactional
    @Modifying
    public String deleteAddress(Integer addressId,Integer userId){
       Optional <Address> address = addressRepository.findById(addressId);
        Optional <Address> user = addressRepository.findById(userId);
        if(address.isPresent() && user.isPresent()) {
            Address address1=address.get();
            address1.setDeleted(true);
            addressRepository.save(address1);

            return "Address deleted";

        }else {
            throw new UserNotFoundException("Address not found");
        }
    }

    @Transactional
    @Modifying
    public  String updateAddress(AddressUpdateDto addressUpdateDto, Integer addressId,Integer userId)
    {
        Optional<Address> address=addressRepository.findById(addressId);
        Optional<Address> user=addressRepository.findById(userId);
        if(address.isPresent() && user.isPresent()) {
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
            return "Wrong address id";
        }
    }
 /* public List<Object[]> findAllAddress(Integer id){
      return addressRepository.findCustomer(id);
  }*/

}
