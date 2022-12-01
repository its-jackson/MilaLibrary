package com.example.application.data.repository;

import com.example.application.data.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface IBookRepository extends JpaRepository<Book, UUID> {

    @Query("select b from Book b " +
            "where lower(b.title) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(b.authorFirstName) like lower(concat('%', :searchTerm, '%'))")
    List<Book> search(@Param("searchTerm") String searchTerm);
}
