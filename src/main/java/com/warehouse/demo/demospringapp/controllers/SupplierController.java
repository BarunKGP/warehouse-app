package com.warehouse.demo.demospringapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.warehouse.demo.demospringapp.domains.Material;
import com.warehouse.demo.demospringapp.domains.Supplier;
import com.warehouse.demo.demospringapp.error.EntityCouldNotBeCreatedException;
import com.warehouse.demo.demospringapp.repos.SupplierRepository;
import com.warehouse.demo.demospringapp.services.SupplierService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/supplier")
@SessionAttributes("suppliers")
@PreAuthorize("hasRole('SUPPLIER')")
public class SupplierController {

    @ModelAttribute("suppliers")
    Supplier getSessionAttributeSupplier() {
        return new Supplier();
    }
    
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping("/{id}/dashboard")
    @PreAuthorize("#supplier.id == principal.id")
    public String showDashboard(@ModelAttribute("suppliers") 
    Supplier supplier, @PathVariable Long id, Model model){
        supplier = supplierService.getCurrentSupplier();
        model.addAttribute("suppliers", supplier);
        return "supplier-dashboard";
    }

    @GetMapping("/{id}/details")
    @PreAuthorize("#supplier.id == principal.id")
    @ResponseBody
    public String getSupplierDetails(@PathVariable Long id,
    @ModelAttribute("suppliers") Supplier supplier) {
        Supplier supp = supplierService.getCurrentSupplier();
        String test = supp.toString();
        return test;

        // return supplier.toString();
    }

    @GetMapping("/{id}/materialaddform")
    @PreAuthorize("#supplier.id == principal.id")
    public String showMaterialAddForm (Model model, @ModelAttribute("suppliers")
    Supplier supplier, Long id) {
        model.addAttribute("suppMat", new Material());
        return "supp-addmaterial";
    }

    @PostMapping("/{s_id}/addmaterial")
    @PreAuthorize("#supplier.id == principal.id")
    public RedirectView addMaterial(@ModelAttribute("suppMat") Material mat,
    @ModelAttribute("suppliers") Supplier supplier, BindingResult errors, 
    RedirectAttributes redirAttr, @PathVariable Long s_id) {
        if(!errors.hasErrors()){
            supplierService.addMaterialToList(mat, s_id);                      
        
        redirAttr.addFlashAttribute("success_supp_mat_add", "The Material has been successfully added to your inventory!");
        return new RedirectView("/supplier/{s_id}/showmaterials");
        }
        redirAttr.addFlashAttribute("success_supp_mat_add", "Sorry, the material could not be added to your inventory");
        throw new EntityCouldNotBeCreatedException("Material with id: " + mat.getId() + " could not be added to inventory", "Please try again");
    }

    @GetMapping("/{id}/showmaterials")
    @PreAuthorize("#supplier.id == principal.id")
    public String showMaterialsList (@ModelAttribute("suppliers") Supplier supplier, 
    Model model, @PathVariable Long id, HttpServletRequest request) {
        if(supplierRepository.findById(id).isPresent()){
            supplier = supplierRepository.findById(id).get();
        }
        
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            String message = (String) inputFlashMap.get("success_supp_mat_add");
            model.addAttribute("message", message);
        }
           

        Iterable<Material> matsList = supplier.getMatsList();
        model.addAttribute("suppMatList", matsList);
        // for (Material mat : matsList) {
        //     System.out.println(mat.getId());
        // } CODE BLOCK FOR TESTING IF THE METHOD WORKS
        return "supp-mat-list";
    }

}