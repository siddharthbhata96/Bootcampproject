package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.dto.CustomerRegisterDto;
import com.ecommerce.Ecommerce.dto.SellerRegisterDto;
import com.ecommerce.Ecommerce.entities.registration.*;
import com.ecommerce.Ecommerce.exception.TokenExpiredException;
import com.ecommerce.Ecommerce.repos.*;
import com.ecommerce.Ecommerce.security.AppUser;
import com.ecommerce.Ecommerce.token.ConfirmationToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<Customer> customer1 = new ArrayList();
    public List<Seller> seller1 = new ArrayList();
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


    public String createCustomer(CustomerRegisterDto customerRegisterDto) {
        Optional<User> existingUser = userRepository.findByEmail(customerRegisterDto.getEmail());
        if (existingUser.isPresent())
        {
            return "This email already exists";
        }
        else
        {
            ModelMapper modelMapper = new ModelMapper();
            Customer customer= modelMapper.map(customerRegisterDto, Customer.class);
            String hpass = customer.getPassword();
            customer.setPassword(passwordEncoder.encode(hpass));
            customer1.add(customer);
            Optional<Role> role1 = roleRepository.findById(2);

            role = role1.get();

            customer.setRoles(Arrays.asList(role));

            userRepository.save(customer);


            ConfirmationToken confirmationToken = new ConfirmationToken(customer);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(customer.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail((mailMessage));
            return "Registration successful";
        }
    }

    //Seller sellSaving
    public String createSupplier(SellerRegisterDto sellerRegisterDto) {
        Optional<User> existingUser = userRepository.findByEmail(sellerRegisterDto.getEmail());
        if (existingUser.isPresent())
        {
            return "This email already exists";
        }
        else {
            ModelMapper modelMapper = new ModelMapper();
            Seller seller= modelMapper.map(sellerRegisterDto, Seller.class);
            String hpass = seller.getPassword();
            seller.setPassword(passwordEncoder.encode(hpass));
            seller1.add(seller);
            Optional<Role> role1 = roleRepository.findById(3);

            role = role1.get();

            seller.setRoles(Arrays.asList(role));
            userRepository.save(seller);

            ConfirmationToken confirmationToken = new ConfirmationToken(seller);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(seller.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setFrom("siddharth.bhatia1996@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail((mailMessage));
            return "Registration successful.Admin will confirm your account";
        }
    }
    @Transactional
    public String confirmAccount(String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        Date presentDate = new Date();
        if (token.getExpiryDate().getTime() - presentDate.getTime() <= 0){
            throw new TokenExpiredException("Token has been expired");
        }
        else{
            Optional<User> user = userRepository.findByEmail(token.getUser().getEmail());
            if(user.isPresent())
            {
                User userobj=user.get();
                userobj.setIs_active(true);
                userRepository.save(userobj);
                confirmationTokenRepository.delConfirmationToken(confirmationToken);
                return "Your account is activated" ;
            }
            return "Your account is activated" ;
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

    public Customer getLoggedInCustomer() {
        User customer;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String email = appUser.getUsername();
        Optional<User>byEmail= userRepository.findByEmail(email);
        customer=byEmail.get();
        Customer customer1=(Customer)customer;

        return customer1;
    }

    public Seller getLoggedInSeller() {
        User seller;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String email = appUser.getUsername();
        Optional<User>byEmail= userRepository.findByEmail(email);
        seller=byEmail.get();
        Seller seller1=(Seller)seller;

        return seller1;
    }
}