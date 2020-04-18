package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.product.Product;
import com.ecommerce.Ecommerce.entities.product.ProductReview;
import com.ecommerce.Ecommerce.entities.registration.Customer;
import com.ecommerce.Ecommerce.entities.registration.User;
import com.ecommerce.Ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.Ecommerce.exception.UserNotFoundException;
import com.ecommerce.Ecommerce.repos.ProductRepository;
import com.ecommerce.Ecommerce.repos.ProductReviewRepository;
import com.ecommerce.Ecommerce.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductReviewDaoService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductReviewRepository productReviewRepository;

    public String createProductReviews(Integer customer_user_id, List<ProductReview> product_reviews, Long product_id) {
        User customer;
        Optional<User> byIdCustomer = userRepository.findById(customer_user_id);
        customer = byIdCustomer.get();

        Product product;
        Optional<Product> byIdProduct = productRepository.findById(product_id);
        product = byIdProduct.get();

        if (!byIdCustomer.isPresent()) {
            throw new UserNotFoundException("User not found");
        } else if (!byIdProduct.isPresent())
            throw new ResourceNotFoundException("Product not found");

        else {
            Customer customer1;
            customer1 = (Customer) customer;

            Customer finalCustomer = customer1;
            product_reviews.forEach(e -> e.setCustomer_name(finalCustomer));


            product_reviews.forEach(e -> e.setProduct(product));

            productReviewRepository.saveAll(product_reviews);
        }
        return "Your review is saved";
    }
}