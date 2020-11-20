package com.warehouse.demo.demospringapp.auth;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.warehouse.demo.demospringapp.domains.Customer;
import com.warehouse.demo.demospringapp.domains.Supplier;



@Entity
public class AuthGroup {
    @Id
    @GeneratedValue
    private long id;
    private String username;
    private String authrole;

    @OneToOne(mappedBy = "authGroup")    
    private Supplier supplier;

    @OneToOne(mappedBy = "authGroup")
    private Customer customer;
    
    
    public Supplier getSupplier() {
        return supplier;
    }

    public Customer getCustomer() {
        return customer;
    }


    public long getId() {
        return id;
    }

    public String getAuthrole() {
        return authrole;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthrole(String authrole) {
        this.authrole = authrole;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AuthGroup() {

    }

    public AuthGroup(String username, String authrole) {
        this.username = username;
        this.authrole = authrole;
    }

    
}
