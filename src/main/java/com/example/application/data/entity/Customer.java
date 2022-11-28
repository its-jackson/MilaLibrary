package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Customer extends AbstractEntity {

    @OneToMany(mappedBy = "customer")
    @Nullable
    private final List<Status> statuses = new LinkedList<>();
    @NotEmpty
    @NotNull
    private String firstName = "";
    @NotEmpty
    @NotNull
    private String lastName = "";
    @NotEmpty
    @NotNull
    private String address = "";
    @NotEmpty
    @NotNull
    private String phone = "";
    @Email
    @NotEmpty
    @NotNull
    private String email = "";

    public Customer() {

    }

    public Customer(
            String firstName,
            String lastName,
            String address,
            String phone,
            String email
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
