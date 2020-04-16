package com.ecommerce.Ecommerce.entities.product;

import com.ecommerce.Ecommerce.entities.registration.Customer;

import javax.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    private Integer quantity;

    private boolean is_wishlist_item;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isIs_wishlist_item() {
        return is_wishlist_item;
    }

    public void setIs_wishlist_item(boolean is_wishlist_item) {
        this.is_wishlist_item = is_wishlist_item;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }
}
