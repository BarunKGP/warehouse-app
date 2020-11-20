package com.warehouse.demo.demospringapp.services;

import com.warehouse.demo.demospringapp.auth.AuthenticationFacade;
import com.warehouse.demo.demospringapp.domains.Material;
import com.warehouse.demo.demospringapp.domains.Supplier;
import com.warehouse.demo.demospringapp.repos.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository; 
    
    @Autowired
    private AuthenticationFacade authenticationFacade;
    
    public Supplier addSupplier(Supplier supplier) {
        if(!supplierRepository.existsById(supplier.getId())) {
           return supplierRepository.save(supplier);
        }
        // else
        //     throws Exception
        return null;
    }

    public void addMaterialToList (Material material, long supp_id) {
        Supplier supp = new Supplier();
        if(supplierRepository.findById(supp_id).isPresent()){
            supp =  supplierRepository.findById(supp_id).get();
        }
        supp.addMaterial(material);
        supplierRepository.save(supp);
    }

    public void removeMaterialFromList (Material material, Supplier supplier) {
        supplier.removeMaterial(material);
    }

    public void deleteSupplier(Supplier supplier){
        //DISSOCIATE ALL MATERIALS ASSOCIATED WITH THE SUPPLIER
        for(int i = 0; i < supplier.getMatsList().size(); i++){     
            supplier.removeMaterial(supplier.getMatsList().get(i));
        }

        supplierRepository.delete(supplier);
    }

    public Supplier getCurrentSupplier() {
        Authentication currentAuth = authenticationFacade.getAuthentication();
        Supplier currentSupplier = supplierRepository.findByUsername(currentAuth.getName());
        return currentSupplier;
    }
}
