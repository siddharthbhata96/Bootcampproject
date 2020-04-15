package com.ecommerce.Ecommerce.repos;


import com.ecommerce.Ecommerce.entities.Order_Details.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders,Integer> {

}
