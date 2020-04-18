package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.CategoryMetadataFieldValues;
import com.ecommerce.Ecommerce.utils.CategoryMetadataFieldValuesID;
import org.springframework.data.repository.CrudRepository;

public interface CategoryMetadataFieldValuesRepository extends CrudRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesID> {
}
