package com.ecommerce.Ecommerce.entities;

import com.ecommerce.Ecommerce.entities.Registration_Details.Admin;
import com.ecommerce.Ecommerce.entities.Registration_Details.Customer;
import com.ecommerce.Ecommerce.entities.Registration_Details.Role;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import com.ecommerce.Ecommerce.repos.AdminRepository;
import com.ecommerce.Ecommerce.repos.RoleRepository;
import com.ecommerce.Ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

@Component
public class Startup implements ApplicationRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserRepository userRepository;

    public Role role = new Role();

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    public void run(ApplicationArguments args) throws Exception {


        Role admin = new Role(1,"ROLE_ADMIN");
        Role customer = new Role(2,"ROLE_CUSTOMER");
        Role seller = new Role(3,"ROLE_SELLER");


        roleRepository.save(admin);
        roleRepository.save(customer);
        roleRepository.save(seller);

        Optional<User>user=userRepository.findByEmail("admin@gmail.com");
        if(user.isPresent())
        {

        }
        else
        {
            saveAdmin();
        }
    }
    public void saveAdmin()
    {
        Admin admin2 =new Admin("admin@gmail.com","Harsh","Kumar","Gupta",true);
        admin2.setPassword(passwordEncoder.encode("admin"));
        Optional<Role> role1 = roleRepository.findById(1);
        role = role1.get();
        admin2.setRoles(Arrays.asList(role));
        adminRepository.save(admin2);
    }
}
