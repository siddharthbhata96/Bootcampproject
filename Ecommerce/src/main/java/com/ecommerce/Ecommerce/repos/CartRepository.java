package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Product_Details.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart,Integer> {

}
