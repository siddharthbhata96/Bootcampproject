package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.registration.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Integer> {
    Page<Customer> findAll(Pageable pageable);

   Optional<Customer> findByEmail(String emailid);
}
