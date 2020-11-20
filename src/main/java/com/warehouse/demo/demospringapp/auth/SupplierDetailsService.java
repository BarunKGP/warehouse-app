package com.warehouse.demo.demospringapp.auth;

import java.util.List;

import com.warehouse.demo.demospringapp.domains.Supplier;
import com.warehouse.demo.demospringapp.repos.SupplierRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SupplierDetailsService implements UserDetailsService {

    private SupplierRepository supplierRepository;
    private AuthGroupRepository authGroupRepository;

    public SupplierDetailsService(SupplierRepository supplierRepository, AuthGroupRepository authGroupRepository) {
        super();
        this.supplierRepository = supplierRepository;
        this.authGroupRepository = authGroupRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Supplier supplier = supplierRepository.findByUsername(username);
        if (supplier == null) {
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        List<AuthGroup> authGroups = authGroupRepository.findByUsername(username);
        return new SupplierPrincipal(supplier, authGroups);
    }
    
    
}
