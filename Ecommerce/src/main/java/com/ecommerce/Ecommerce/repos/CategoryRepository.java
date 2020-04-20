package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long> {
    @Query("from Category")
    List<Category> listCategory();

    Optional<Category> findByName(String name);

    @Query(value = "select name from category where name=:cname", nativeQuery = true)
    String findByCatName(@Param("cname") String cname);
}
