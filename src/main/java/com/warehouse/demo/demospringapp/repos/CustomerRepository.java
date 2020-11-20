package com.warehouse.demo.demospringapp.repos;

import com.warehouse.demo.demospringapp.domains.Customer;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
    public boolean existsByUsername(String username);
    public Customer findByUsername(String username);    
}