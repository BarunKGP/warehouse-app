package com.warehouse.demo.demospringapp.services;

import com.warehouse.demo.demospringapp.DTOs.Cart;
import com.warehouse.demo.demospringapp.DTOs.OrderDTO;
import com.warehouse.demo.demospringapp.auth.AuthenticationFacade;
import com.warehouse.demo.demospringapp.domains.Customer;
import com.warehouse.demo.demospringapp.error.EntityNotFoundException;
import com.warehouse.demo.demospringapp.repos.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    public Customer addCustomer(Customer customer) {
        if(customer != null){
            Customer newCustomer = customerRepository.save(customer);
            return newCustomer;
        }
        throw new EntityNotFoundException("Null value", "Please enter valid details for profile creation");
    }
    
    public Customer getCurrentCustomer() {
        Authentication currentAuth = authenticationFacade.getAuthentication();
        Customer currentCustomer = customerRepository.findByUsername(currentAuth.getName());
        return currentCustomer;
    }

    public Cart addOrdertoCart(Long custID, OrderDTO order) {
        Cart cart = new Cart(custID);
        cart.addOrder(order);
        return cart;
    }

}
