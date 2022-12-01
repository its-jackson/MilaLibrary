package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
public class Book extends AbstractEntity {

    @NotBlank
    @NotEmpty
    private String title = "";
    @NotBlank
    @NotEmpty
    private String authorFirstName = "";
    @NotBlank
    @NotEmpty
    private String authorLastName = "";

    private int rating = 1;

    public Book() {

    }

    public Book(
            String title,
            String authorFirstName,
            String authorLastName,
            int rating
    ) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
