package com.ecommerce.Ecommerce.entities.registration;

import com.ecommerce.Ecommerce.entities.product.ProductReview;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
    private String  contact;

    @OneToMany(mappedBy = "customer_name", cascade = CascadeType.ALL)
    private List<ProductReview> reviews;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<ProductReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ProductReview> reviews) {
        this.reviews = reviews;
    }

}
