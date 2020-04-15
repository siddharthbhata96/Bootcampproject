
package com.ecommerce.Ecommerce.entities.Product_Details;

import com.ecommerce.Ecommerce.entities.Registration_Details.Seller;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "increment")
    private int id;
    private String name;
    private String description;
    private boolean is_cancellable;
    private boolean is_returnable;
    private String brand;
    private boolean is_active;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="seller_user_id")
    private Seller seller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Product_Variation> product_variation=new ArrayList();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Product_Review>product_reviews=new ArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_cancellable() {
        return is_cancellable;
    }

    public void setIs_cancellable(boolean is_cancellable) {
        this.is_cancellable = is_cancellable;
    }

    public boolean isIs_returnable() {
        return is_returnable;
    }

    public void setIs_returnable(boolean is_returnable) {
        this.is_returnable = is_returnable;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public List<Product_Variation> getProduct_variation() {
        return product_variation;
    }

    public void setProduct_variation(List<Product_Variation> product_variation) {
        this.product_variation = product_variation;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product_Review> getProduct_reviews() {
        return product_reviews;
    }

    public void setProduct_reviews(List<Product_Review> product_reviews) {
        this.product_reviews = product_reviews;
    }
}

