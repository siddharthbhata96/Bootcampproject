package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Integer> {
}
