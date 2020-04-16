package com.ecommerce.Ecommerce.repos;


import com.ecommerce.Ecommerce.entities.order.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Orders,Integer> {

}
