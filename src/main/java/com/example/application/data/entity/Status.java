package com.example.application.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

enum BookState {
    CHECK_IN("Check-in"),
    CHECK_OUT("Check-out");

    BookState(String s) {

    }
}

@Entity
public class Status extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull
    @JsonIgnoreProperties
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @NotNull
    @JsonIgnoreProperties
    private Book book;

    @NotEmpty
    private Date date;

    @NotNull
    @NotEmpty
    private BookState bookState;

    public Status() {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BookState getBookState() {
        return bookState;
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }
}
