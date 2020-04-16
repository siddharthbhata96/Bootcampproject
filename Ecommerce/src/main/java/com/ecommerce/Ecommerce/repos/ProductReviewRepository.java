package com.ecommerce.Ecommerce.repos;

import com.ecommerce.Ecommerce.entities.product.ProductReview;
import org.springframework.data.repository.CrudRepository;

public interface ProductReviewRepository extends CrudRepository<ProductReview,Integer> {
}
