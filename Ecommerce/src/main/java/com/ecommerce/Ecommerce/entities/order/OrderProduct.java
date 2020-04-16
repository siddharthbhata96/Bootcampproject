package com.ecommerce.Ecommerce.entities.order;

import com.ecommerce.Ecommerce.entities.product.ProductVariation;

import javax.persistence.*;

@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    private Integer quantity;
    private Double price;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id")
    private ProductVariation product_variation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    private Orders orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductVariation getProduct_variation() {
        return product_variation;
    }

    public void setProduct_variation(ProductVariation product_variation) {
        this.product_variation = product_variation;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
