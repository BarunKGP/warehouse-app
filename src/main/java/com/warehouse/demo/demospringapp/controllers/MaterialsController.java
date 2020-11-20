package com.warehouse.demo.demospringapp.controllers;

import com.warehouse.demo.demospringapp.DTOs.OrderDTO;
import com.warehouse.demo.demospringapp.domains.Material;
import com.warehouse.demo.demospringapp.error.EntityNotFoundException;
import com.warehouse.demo.demospringapp.services.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/materials")
public class MaterialsController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/")
    public String getAllMaterials(Model model){
        model.addAttribute("materialall", materialService.getAllMaterials());
        model.addAttribute("order", new OrderDTO());
        return "material-list";
    }

    @GetMapping("/search")
    public String searchMaterials (Model model) {
         model.addAttribute("materialsearch", new Material());
         model.addAttribute("order", new OrderDTO());
         return "material-search";
     }

     @PostMapping("/search")
     public String searchMaterialsResult (Model model, @RequestParam(value = "name") String name) {
         Iterable<Material> foundMaterials = materialService.searchMaterialsByName(name);
         if(foundMaterials == null) {
             throw new EntityNotFoundException("Material with name: " + name + " does not exist");
         }      
         model.addAttribute("foundMaterials", foundMaterials);
         return "material-search-found";
     }    
}
