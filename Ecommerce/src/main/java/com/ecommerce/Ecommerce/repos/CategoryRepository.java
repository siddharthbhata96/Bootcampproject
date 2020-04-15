package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.Product_Details.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {
    @Query("from Category")
    List<Category> listCategory();

    Optional<Category> findByName(String name);
}
