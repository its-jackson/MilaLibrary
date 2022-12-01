package com.example.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Log extends AbstractEntity {

    @NotEmpty
    @NotBlank
    private String state = "";

    private LocalDateTime checkOutDate = LocalDateTime.now();

    private LocalDateTime checkInDate;

    @ManyToOne
    @NotNull
    private Book book;

    @ManyToOne
    @NotNull
    @JsonIgnoreProperties({"logs"})
    private Customer customer;

    public Log() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public enum State {
        CHECK_IN("Check-in"),
        CHECK_OUT("Check-out");

        private final String state;

        State(String s) {
            this.state = s;
        }

        public String getState() {
            return state;
        }
    }
}
