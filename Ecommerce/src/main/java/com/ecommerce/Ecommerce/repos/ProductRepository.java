package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Long> {
}
