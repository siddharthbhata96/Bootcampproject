package com.ecommerce.Ecommerce.entities.Order_Details;

import com.ecommerce.Ecommerce.entities.Product_Details.Product_Variation;

import javax.persistence.*;

@Entity
public class Order_Product {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    private Integer quantity;
    private Double price;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variation_id")
    private Product_Variation product_variation;

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

    public Product_Variation getProduct_variation() {
        return product_variation;
    }

    public void setProduct_variation(Product_Variation product_variation) {
        this.product_variation = product_variation;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
