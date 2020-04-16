package com.ecommerce.Ecommerce.dto;


import com.ecommerce.Ecommerce.entities.registration.Address;
import com.ecommerce.Ecommerce.validations.PasswordMatches;
import com.ecommerce.Ecommerce.validations.Phone;
import com.ecommerce.Ecommerce.validations.ValidPassword;
import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

@PasswordMatches
@JsonFilter("CustomerDto-Filter")
public class CustomerRegisterDto {

    @NotNull
     private String firstName;
     private String middleName;
     @NotNull
     private String lastName;

    @Column(unique = true)
     @Email
    private String email;

    private Set<Address> addresses;

    @Phone
    private String contact;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
