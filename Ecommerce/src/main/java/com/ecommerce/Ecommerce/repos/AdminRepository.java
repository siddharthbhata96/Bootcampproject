package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Registration_Details.Admin;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin,Integer> {

}
