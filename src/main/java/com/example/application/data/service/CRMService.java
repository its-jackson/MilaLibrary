package com.example.application.data.service;

import com.example.application.data.entity.Customer;
import com.example.application.data.repository.ICustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CRMService {
    private final ICustomerRepository customerRepository;

    public CRMService(
            ICustomerRepository customerRepository
    ) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAllCustomers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return customerRepository.findAll();
        } else {
            return customerRepository.search(stringFilter);
        }
    }

    public long countCustomers() {
        return customerRepository.count();
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public void saveCustomer(Customer customer) {
        if (customer == null) {
            System.err.println("Customer is null. Are you sure you have connected your form to the application?");
            return;
        }
        customerRepository.save(customer);
    }
}