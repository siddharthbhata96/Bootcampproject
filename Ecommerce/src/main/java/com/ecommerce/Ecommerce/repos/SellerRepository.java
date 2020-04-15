package com.ecommerce.Ecommerce.repos;
import com.ecommerce.Ecommerce.entities.Registration_Details.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends CrudRepository<Seller,Integer> {
    Page<Seller> findAll(Pageable page);
}
