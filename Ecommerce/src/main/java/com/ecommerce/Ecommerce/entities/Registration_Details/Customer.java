package com.ecommerce.Ecommerce.entities.Registration_Details;

import com.ecommerce.Ecommerce.entities.Product_Details.Product_Review;

import javax.persistence.*;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
    private long  contact;

    @OneToMany(mappedBy = "customer_name", cascade = CascadeType.ALL)
    private List<Product_Review> reviews;

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public List<Product_Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Product_Review> reviews) {
        this.reviews = reviews;
    }

}
