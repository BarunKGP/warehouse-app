package com.warehouse.demo.demospringapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.warehouse.demo.demospringapp.auth.AuthGroupRepository;
import com.warehouse.demo.demospringapp.auth.AuthenticationFacade;
import com.warehouse.demo.demospringapp.domains.Customer;
import com.warehouse.demo.demospringapp.repos.CustomerRepository;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CustomerServiceUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AuthGroupRepository authRepo;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private CustomerService customerService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCustomerCreation() {
        Customer testCustomer = new Customer("John", "Doe", "jdoe@email.com", "password");
        
        //Stubbing the mocked repo
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);
        
        //Save the customer
        Customer newCustomer = customerService.addCustomer(testCustomer);
        
        //Verify that it works properly
        assertEquals("John", newCustomer.getFirstname());
        assertEquals("Doe", newCustomer.getLastname());
        assertEquals("jdoe@email.com", newCustomer.getEmail());
        assertEquals(newCustomer.getUsername(), newCustomer.getEmail());
        assertEquals("password", newCustomer.getPassword());
    }
}
