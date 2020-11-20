package com.warehouse.demo.demospringapp.repos;


import com.warehouse.demo.demospringapp.domains.Supplier;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "supp-page", path = "supp-page")
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

    public Supplier findByUsername(String username);
    public boolean existsByUsername(String username);
    
}
