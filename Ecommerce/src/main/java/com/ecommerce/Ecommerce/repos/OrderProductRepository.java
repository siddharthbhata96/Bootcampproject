package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.order.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct,Integer> {
}
