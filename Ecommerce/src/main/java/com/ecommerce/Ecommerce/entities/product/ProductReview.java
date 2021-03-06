package com.ecommerce.Ecommerce.entities.product;

import com.ecommerce.Ecommerce.entities.registration.Customer;

import javax.persistence.*;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    private String review;
    private Double rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_user_id")
    private Customer customer_name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;


    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Customer getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(Customer customer_name) {
        this.customer_name = customer_name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
