package com.warehouse.demo.demospringapp.repos;

import java.util.List;
import java.util.Optional;

import com.warehouse.demo.demospringapp.domains.Material;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "mat-page", path = "mat-page")
public interface MaterialRepository extends CrudRepository<Material, Integer> { 
    
    public Optional<Material> findById(long id);
    
    public List<Material> findByName(String name);

    public boolean existsById(long id);
    
    public boolean existsByName(String name);

}
