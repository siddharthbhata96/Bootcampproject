package com.ecommerce.Ecommerce.entities.Product_Details;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Product_Variation {
    @Id
    @GeneratedValue(generator = "increment")
    private int id;
    private String quantity_available;
    private double price;
    private String primary_image_name;
    private boolean is_active;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(String quantity_available) {
        this.quantity_available = quantity_available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPrimary_image_name() {
        return primary_image_name;
    }

    public void setPrimary_image_name(String primary_image_name) {
        this.primary_image_name = primary_image_name;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
