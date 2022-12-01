package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Customer;
import com.example.application.data.repository.IBookRepository;
import com.example.application.data.repository.ICustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CRMService {

    private final ICustomerRepository customerRepository;
    private final IBookRepository bookRepository;

    public CRMService(
            ICustomerRepository customerRepository,
            IBookRepository bookRepository
    ) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    public List<Customer> findAllCustomers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty())
            return customerRepository.findAll();
        else
            return customerRepository.search(stringFilter);
    }

    public long countCustomers() {
        return customerRepository.count();
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public void saveCustomer(Customer customer) {
        if (customer == null) return;
        customerRepository.save(customer);
    }

    public List<Book> findAllBooks(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty())
            return bookRepository.findAll();
        else
            return bookRepository.search(stringFilter);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public void saveBook(Book book) {
        if (book == null) return;
        bookRepository.save(book);
    }
}