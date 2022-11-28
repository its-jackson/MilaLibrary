package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Book extends AbstractEntity {
    @OneToMany(mappedBy = "book")
    @Nullable
    private final List<Status> statuses = new LinkedList<>();
    @NotBlank
    private String title;
    @NotBlank
    private String authorFirstName;
    @NotBlank
    private String authorLastName;
    @Size(min = 1, max = 5)
    private int rating;

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

    @Nullable
    public List<Status> getStatuses() {
        return statuses;
    }
}
