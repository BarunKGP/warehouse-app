package com.warehouse.demo.demospringapp.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.warehouse.demo.demospringapp.domains.Customer;
import com.warehouse.demo.demospringapp.domains.Supplier;
import com.warehouse.demo.demospringapp.domains.User;
import com.warehouse.demo.demospringapp.domains.User_Type;
import com.warehouse.demo.demospringapp.error.EntityCouldNotBeCreatedException;
import com.warehouse.demo.demospringapp.services.CustomerService;
import com.warehouse.demo.demospringapp.services.SupplierService;
import com.warehouse.demo.demospringapp.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserAuthController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        // model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout-success")
    public String getLogoutPage (Model model) {
        return "logout";
    }

    @GetMapping("/signup")
    public String getSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; //CREATE signup.html
    }

    @PostMapping("/signup")
    public RedirectView assignSignupUser(@ModelAttribute("user") User user, RedirectAttributes redirAttr,
    BindingResult errors) {

        userService.validateUserByUsername(user.getEmail());

        if(user.getType() == User_Type.SUPPLIER) {   
            if(!errors.hasErrors()) {
                
                Supplier initSupplier = new Supplier(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
                initSupplier.setPassword(new BCryptPasswordEncoder().encode(initSupplier.getPassword()));
                initSupplier.addAuthGroup();
                Supplier supplier = supplierService.addSupplier(initSupplier);
                redirAttr.addFlashAttribute("success_supp", supplier);
                return new RedirectView("/signup/supplier-success");
            }                 
        
            throw new EntityCouldNotBeCreatedException("Object of type: Supplier could not be created");            
        }   

        //ADD FLASH ATTRIBUTES
        if(user.getType() == User_Type.CUSTOMER) {  
            if(!errors.hasErrors()) {
                Customer initCustomer = new Customer(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword());
                initCustomer.setPassword(new BCryptPasswordEncoder().encode(initCustomer.getPassword()));
                initCustomer.addAuthGroup();
                Customer customer = customerService.addCustomer(initCustomer);
                redirAttr.addFlashAttribute("success_cust", customer);
                return new RedirectView("/signup/customer-success"); 
            }
            
            throw new EntityCouldNotBeCreatedException("Object of type: Customer could not be created");        
        }
        
        throw new EntityCouldNotBeCreatedException("The profile could not be created");        
    }

    @GetMapping("/signup/customer-success")
    public String customerSuccess (HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            Customer customer = (Customer) inputFlashMap.get("success_cust");
            model.addAttribute("cust_success", customer);
            return "cust-welcome";
        }
        else 
            throw new EntityCouldNotBeCreatedException("Your Customer profile could not be created", "Please try again");
    }

    @GetMapping("/signup/supplier-success")
    public String supplierSuccess (HttpServletRequest request, Model model) {
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if (inputFlashMap != null) {
            Supplier supplier = (Supplier) inputFlashMap.get("success_supp");
            model.addAttribute("supp_success", supplier);
            return "supp-welcome";
        }
        else 
            throw new EntityCouldNotBeCreatedException("Your Supplier profile could not be created", "Please try again");
    }

    
}
