package com.ecommerce.Ecommerce.dto;



import com.ecommerce.Ecommerce.validations.Phone;
import com.ecommerce.Ecommerce.validations.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SellerRegisterDto {

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @Email
    private String email;

    @Phone
    private String contact;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    private String confirmPassword;

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
