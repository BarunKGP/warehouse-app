package com.warehouse.demo.demospringapp.services;

import com.warehouse.demo.demospringapp.error.EntityAlreadyExistsException;
import com.warehouse.demo.demospringapp.repos.CustomerRepository;
import com.warehouse.demo.demospringapp.repos.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    public void validateUserByUsername(String username) {
        if(customerRepository.existsByUsername(username) ||
        supplierRepository.existsByUsername(username)) {
            throw new EntityAlreadyExistsException("Username already exists", "Please sign in with your credentials");
        }
        return;
    }
    
}
