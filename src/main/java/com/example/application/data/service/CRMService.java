package com.example.application.data.service;

import com.example.application.data.entity.Book;
import com.example.application.data.entity.Customer;
import com.example.application.data.entity.Log;
import com.example.application.data.repository.IBookRepository;
import com.example.application.data.repository.ICustomerRepository;
import com.example.application.data.repository.ILogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CRMService {

    private final ICustomerRepository customerRepository;
    private final IBookRepository bookRepository;
    private final ILogRepository logRepository;

    public CRMService(
            ICustomerRepository customerRepository,
            IBookRepository bookRepository,
            ILogRepository logRepository
    ) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.logRepository = logRepository;
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

    public long countBooks() {
        return bookRepository.count();
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public void saveBook(Book book) {
        if (book == null) return;
        bookRepository.save(book);
    }

    public List<Log> findAllLogs() {
        return logRepository.findAll();
    }

    public long countLogs() {
        return logRepository.count();
    }

    public void deleteLog(Log log) {
        logRepository.delete(log);
    }

    public void saveLog(Log log) {
        if (log == null) return;
        logRepository.save(log);
    }
}