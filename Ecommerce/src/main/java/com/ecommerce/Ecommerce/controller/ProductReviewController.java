package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.entities.product.ProductReview;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.services.ProductReviewDaoService;
import com.ecommerce.Ecommerce.services.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductReviewController {

    @Autowired
    ProductReviewDaoService data;

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("/customer/productReviewSave/{product_id}")
    public String saveProductsReviews(@RequestBody List<ProductReview> product_reviews, @PathVariable(value="product_id")Long product_id)
    {
        Customer customer=userDaoService.getLoggedInCustomer();
        Integer customer_user_id = customer.getId();
        String product_reviews1= data.createProductReviews(customer_user_id,product_reviews, product_id);
        return product_reviews1;
    }
}
