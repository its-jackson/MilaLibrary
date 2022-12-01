package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Customer extends AbstractEntity {

    @OneToMany(mappedBy = "customer")
    @Nullable
    private final List<Log> logs = new LinkedList<>();
    @NotEmpty
    @NotBlank
    @NotNull
    private String firstName = "";
    @NotEmpty
    @NotBlank
    @NotNull
    private String lastName = "";
    @NotEmpty
    @NotBlank
    @NotNull
    private String address = "";
    @NotEmpty
    @NotBlank
    @NotNull
    private String phone = "";
    @Email
    @NotEmpty
    @NotBlank
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
        return firstName + " " + lastName + " " + email;
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

    @Nullable
    public List<Log> getLogs() {
        return logs;
    }
}
