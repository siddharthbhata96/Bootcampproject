
package com.ecommerce.Ecommerce.entities.Order_Details;

import com.ecommerce.Ecommerce.entities.Registration_Details.Customer;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Orders {
    @Id
    @GeneratedValue(generator = "increment")
    private Integer id;
    private Integer amount_paid;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_created;
    private String payment_method;
    private String customer_address_city;
    private String customer_address_state;
    private String customer_address_country;
    private String customer_address_address_line;
    private String customer_address_zipcode;
    private String customer_address_label;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="customer_user_id")
    private Customer customer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(Integer amount_paid) {
        this.amount_paid = amount_paid;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getCustomer_address_city() {
        return customer_address_city;
    }

    public void setCustomer_address_city(String customer_address_city) {
        this.customer_address_city = customer_address_city;
    }

    public String getCustomer_address_state() {
        return customer_address_state;
    }

    public void setCustomer_address_state(String customer_address_state) {
        this.customer_address_state = customer_address_state;
    }

    public String getCustomer_address_country() {
        return customer_address_country;
    }

    public void setCustomer_address_country(String customer_address_country) {
        this.customer_address_country = customer_address_country;
    }

    public String getCustomer_address_address_line() {
        return customer_address_address_line;
    }

    public void setCustomer_address_address_line(String customer_address_address_line) {
        this.customer_address_address_line = customer_address_address_line;
    }

    public String getCustomer_address_zipcode() {
        return customer_address_zipcode;
    }

    public void setCustomer_address_zipcode(String customer_address_zipcode) {
        this.customer_address_zipcode = customer_address_zipcode;
    }

    public String getCustomer_address_label() {
        return customer_address_label;
    }

    public void setCustomer_address_label(String customer_address_label) {
        this.customer_address_label = customer_address_label;
    }

   public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}

