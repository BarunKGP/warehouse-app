package com.warehouse.demo.demospringapp.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.warehouse.demo.demospringapp.domains.Customer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerPrincipal implements UserDetails {
    
    private static final long serialVersionUID = 1L;
    private Customer customer;
    private List<AuthGroup> authGroups;

    public CustomerPrincipal(Customer customer, List<AuthGroup> authGroups) {
        super();
        this.customer = customer;
        this.authGroups = authGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authGroups == null) {
            return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        authGroups.forEach(group->{
            grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthrole()));
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.customer.getPassword();
    }

    @Override
    public String getUsername() {
        return this.customer.getUsername();
    }

    public Long getId() {
        return this.customer.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
