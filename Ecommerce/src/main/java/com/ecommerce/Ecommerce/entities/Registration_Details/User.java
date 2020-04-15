package com.ecommerce.Ecommerce.entities.Registration_Details;


import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonFilter("userFilter")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String email;
    private String first_name;
    private String middle_name;
    private String last_name;
   // @Pattern(regexp="^[a-zA-Z0-9]{8,15}",message="Passowrd should be alfa numeric and length should be between 8 and 15")
    private String password;
    private boolean is_deleted=false;
    private boolean is_active=false;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Address> address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private List<Role> roles;

    public User()
    {

    }
    public User(User user) {
        this.id=user.id;
        this.email=user.email;
        this.first_name=user.first_name;
        this.middle_name=user.middle_name;
        this.last_name=user.last_name;
        this.password=user.password;
        this.is_deleted=user.is_deleted;
        this.is_active=user.is_active;
        this.roles=user.roles;
    }

    public User(String email, String first_name, String middle_name, String last_name,boolean is_active) {
        this.email=email;
        this.first_name=first_name;
        this.middle_name=middle_name;
        this.last_name=last_name;
        this.is_active=is_active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", middle_name='" + middle_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", password='" + password + '\'' +
                ", is_deleted=" + is_deleted +
                ", is_active=" + is_active +
                ", address=" + address +
                ", roles=" + roles +
                '}';
    }
}
