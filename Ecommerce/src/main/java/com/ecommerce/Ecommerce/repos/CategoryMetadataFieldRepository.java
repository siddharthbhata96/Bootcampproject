package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.CategoryMetadataField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMetadataFieldRepository extends CrudRepository<CategoryMetadataField, Long> {
    CategoryMetadataField findByName(String fieldName);

    List<CategoryMetadataField> findAll();

    List<CategoryMetadataField> findAll(Pageable pageable);
}
