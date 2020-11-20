package com.warehouse.demo.demospringapp.auth;

import java.util.List;

import com.warehouse.demo.demospringapp.domains.Customer;
import com.warehouse.demo.demospringapp.repos.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private CustomerRepository customerRepository;
    private AuthGroupRepository authGroupRepository;

    @Autowired
    public CustomerDetailsService(CustomerRepository customerRepository,
    AuthGroupRepository authGroupRepository) {
        this.customerRepository = customerRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        List<AuthGroup> authGroups = authGroupRepository.findByUsername(username);
        return new CustomerPrincipal(customer, authGroups);        
    }

    
}
