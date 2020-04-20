package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {

  @Query("from Product")
  Product findByProductName(String name);


  @Query(value = "select * from product where seller_user_id=:sellerid",nativeQuery = true)
  List<Product> findSellerAssociatedProducts(@Param("sellerid") Integer sellerid);

  @Query(value = "select * from product where category_id IN (select category_id from category where name = :category)" , nativeQuery = true)
  List<Product> findAllProducts(@Param("category") String category);
}
