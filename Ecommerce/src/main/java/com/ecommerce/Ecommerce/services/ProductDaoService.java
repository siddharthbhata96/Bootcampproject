package com.ecommerce.Ecommerce.services;

import com.ecommerce.Ecommerce.entities.Product_Details.*;
import com.ecommerce.Ecommerce.entities.Registration_Details.Customer;
import com.ecommerce.Ecommerce.entities.Registration_Details.Seller;
import com.ecommerce.Ecommerce.entities.Registration_Details.User;
import com.ecommerce.Ecommerce.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDaoService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Category category;

    @Autowired
    Product_VariationRepository product_variationRepository;

    @Autowired
    Product_ReviewRepository product_reviewRepository;

    @Autowired
    CartRepository cartRepository;

    public String categorySave(Category categorySave)
    {
        categoryRepository.save(categorySave);
        return "Category saved successfully";
    }
    public String categoryParentSave(Integer parent_id,Category categorySave)
    {
        Optional<Category> byId=categoryRepository.findById(parent_id);
        Category category1;
        category1=byId.get();
        categorySave.setParentCategory(category1);
        categoryRepository.save(categorySave);
        return "Category saved successfully";
    }

    public List<Category> retrieveAllCategory()
    {
        return categoryRepository.listCategory();
    }

    public List<Product> createProduct(Integer user_id,List<Product> product,String category_name)
    {
        User sell;
        Optional<User>byId=userRepository.findById(user_id);
        sell=byId.get();

        Seller sell2;
        sell2=(Seller)sell;

        Seller finalSell=sell2;
        product.forEach(e->e.setSeller(finalSell));

        Optional<Category> category1 = categoryRepository.findByName(category_name);
        category=category1.get();

        product.forEach(e->e.setCategory(category));


        productRepository.saveAll(product);
        return  product;

    }

    public List<Product_Variation> createProductVariation(Integer id,List<Product_Variation>product_variations)
    {
        Product product;
        Optional<Product>byId=productRepository.findById(id);
        product=byId.get();
        product_variations.forEach((e->e.setProduct(product)));
        product_variationRepository.saveAll(product_variations);
        return product_variations;
    }

    public List<Product_Review> createProductReviews(Integer customer_user_id,List<Product_Review> product_reviews,Integer product_id)
    {
        User customer;
        Optional<User>byIdCustomer=userRepository.findById(customer_user_id);
        customer=byIdCustomer.get();

        Customer customer1;
        customer1=(Customer)customer;

        Customer finalCustomer=customer1;
        product_reviews.forEach(e->e.setCustomer_name(finalCustomer));

        Product product;
        Optional<Product>byIdProduct=productRepository.findById(product_id);
        product=byIdProduct.get();
        product_reviews.forEach(e->e.setProduct(product));

        product_reviewRepository.saveAll(product_reviews);
        return product_reviews;
    }

    public Cart addToCart(Integer customer_user_id, Cart cart, Integer productVariation_id){

        Optional<User> customer = userRepository.findById(customer_user_id);

        User user;
        user=customer.get();

        Customer customer1;
        customer1=(Customer)user;

        cart.setCustomer(customer1);

        Optional<Product_Variation> productVariation= product_variationRepository.findById(productVariation_id);
        Product_Variation productVariation1;
        productVariation1= productVariation.get();

        cart.setProductVariation(productVariation1);

        cartRepository.save(cart);

        return cart;
    }
}
