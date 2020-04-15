package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Product_Details.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
}
