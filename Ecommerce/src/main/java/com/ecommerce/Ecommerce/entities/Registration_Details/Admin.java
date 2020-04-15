package com.ecommerce.Ecommerce.entities.Registration_Details;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    Admin()
    {

    }

    public Admin(String email,String first_name,String middle_name,String last_name,boolean is_active)
    {
        super(email,first_name,middle_name,last_name,is_active);
    }
}
