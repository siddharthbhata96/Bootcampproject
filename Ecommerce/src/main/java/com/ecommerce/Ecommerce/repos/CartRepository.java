package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart,Integer> {

}
