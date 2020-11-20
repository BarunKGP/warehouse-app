package com.warehouse.demo.demospringapp.services;

import java.util.ArrayList;
import java.util.List;

import com.warehouse.demo.demospringapp.domains.Material;
import com.warehouse.demo.demospringapp.repos.MaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    private MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        super();
        this.materialRepository = materialRepository;
    }

    public Material updateMaterials (Material material){
        if (!materialRepository.existsById(material.getId())) {
            materialRepository.save(material);
        }

        return null;
    }

    // public Material updateMaterials (int id, String name, String details){
    //     if (!materialRepository.existsById(id)) {
    //         materialRepository.save(new Material(id, name, details));
    //     }

    //     return null;
    // }
    
    public Iterable<Material> getAllMaterials (){
        // List<Materials> materials = new ArrayList<> ();
        return this.materialRepository.findAll();
    }

    // public Material findMaterialByName (String name) {
    //     if (materialRepository.findByName(name).isPresent()) {
    //         return materialRepository.findByName(name).get();
    //     }
    //     return null;
    // }

    public List<Material> searchMaterialsByName (String name) {
        List<Material> materialList = new ArrayList<> ();
        for (Material mat : materialRepository.findAll()) {
            if (mat.getName().equals(name)) {
                materialList.add(mat);
            }
        }
        if (materialList.isEmpty())
            return null;
        else
           return materialList;
    }

    
}
