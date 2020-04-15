package com.ecommerce.Ecommerce.entities.Registration_Details;

import com.ecommerce.Ecommerce.entities.Product_Details.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "user_id")
public class Seller extends User {
    private String GST;
    private long company_contact;
    private String company_name;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private List<Product> product=new ArrayList();

    public String getGST() {
        return GST;
    }

    public void setGST(String GST) {
        this.GST = GST;
    }

    public long getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(long company_contact) {
        this.company_contact = company_contact;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
