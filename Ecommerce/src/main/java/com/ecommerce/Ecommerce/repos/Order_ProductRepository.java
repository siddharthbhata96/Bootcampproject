package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Order_Details.Order_Product;
import org.springframework.data.repository.CrudRepository;

public interface Order_ProductRepository extends CrudRepository<Order_Product,Integer> {
}
