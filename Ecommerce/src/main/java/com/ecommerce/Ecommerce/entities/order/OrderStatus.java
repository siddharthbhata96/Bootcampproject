package com.ecommerce.Ecommerce.entities.order;

import javax.persistence.*;

@Entity
public class OrderStatus {
    @Id
    @GeneratedValue(generator = "increment")
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    private String fromStatus;
    private String toStatus;
    private String transitionNotesComments;

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public String getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(String fromStatus) {
        this.fromStatus = fromStatus;
    }

    public String getToStatus() {
        return toStatus;
    }

    public void setToStatus(String toStatus) {
        this.toStatus = toStatus;
    }

    public String getTransitionNotesComments() {
        return transitionNotesComments;
    }

    public void setTransitionNotesComments(String transitionNotesComments) {
        this.transitionNotesComments = transitionNotesComments;
    }
}
