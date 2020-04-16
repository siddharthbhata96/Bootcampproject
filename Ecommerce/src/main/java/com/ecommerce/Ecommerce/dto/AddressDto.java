package com.ecommerce.Ecommerce.dto;

import com.ecommerce.Ecommerce.entities.registration.User;

import javax.validation.constraints.NotNull;

public class AddressDto {

    @NotNull
    private String house_number;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String country;
    @NotNull
    private String zip_code;
    @NotNull
    private String label;

    private User user;


    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public User getUser(User user) {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
