package com.ecommerce.Ecommerce.dto;


import com.ecommerce.Ecommerce.entities.registration.Address;
import com.ecommerce.Ecommerce.validations.Password;
import com.ecommerce.Ecommerce.validations.PasswordMatches;
import com.ecommerce.Ecommerce.validations.Phone;
import com.ecommerce.Ecommerce.validations.ValidPassword;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@PasswordMatches
public class CustomerRegisterDto {

    @Column(nullable = false)
     private String first_name;
    private String middle_name;
     @NotNull
     private String last_name;

    @Column(unique = true)
     @Email
    private String email;

    private Set<Address> addresses;

    @Phone
    private String contact;

    @NotNull
    @Password
    private String password;

    @NotNull
    private String confirmPassword;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
