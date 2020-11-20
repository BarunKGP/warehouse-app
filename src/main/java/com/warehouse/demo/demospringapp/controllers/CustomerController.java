package com.warehouse.demo.demospringapp.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.warehouse.demo.demospringapp.DTOs.Cart;
import com.warehouse.demo.demospringapp.DTOs.InvoiceDTO;
import com.warehouse.demo.demospringapp.DTOs.OrderDTO;
import com.warehouse.demo.demospringapp.domains.Customer;
import com.warehouse.demo.demospringapp.error.InvalidOrderException;
import com.warehouse.demo.demospringapp.repos.MaterialRepository;
import com.warehouse.demo.demospringapp.services.CustomerService;
import com.warehouse.demo.demospringapp.services.OrderDTOService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
@SessionAttributes("customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private OrderDTOService orderService;

    private Map<String, Object> objMap = new HashMap<>();
    private Map<String, List<OrderDTO>> orderMap = new HashMap<>();
    private List<OrderDTO> activeOrders = new ArrayList<>();

    @ModelAttribute("customers")
    public Customer getSessionAttributeCustomer() {
        return new Customer();
    }

    @GetMapping("/{id}")
    @PreAuthorize("#customer.id == principal.id")
    public String getProfile (@PathVariable Long id, 
    @ModelAttribute("customers") Customer customer, Model model) {
        customer = customerService.getCurrentCustomer();
        model.addAttribute("customers", customer);
        return "cust-profile";
    }

    @GetMapping("/{id}/details")
    @ResponseBody
    @PreAuthorize("#customer.id == principal.id")
    public String getCustomerDetails(@PathVariable Long id, @ModelAttribute("customers") Customer customer) {
        return customer.toString();
    }

    @PostMapping("/{id}/buy")
    @PreAuthorize("#customer.id == principal.id")
    public String createBuyOrder(@PathVariable(name = "id") Long custId,
    @ModelAttribute("customers") Customer customer,
    @ModelAttribute("order") OrderDTO order, Model model) {

        //Validate and process order
        order = orderService.processOrder(custId, order.getMatId(), order.getUnits());
        model.addAttribute("order", order);
        ListIterator<OrderDTO> iter = activeOrders.listIterator();
        while (iter.hasNext()) {
            if(iter.next().getCustId() != custId) {
                iter.remove();
            }
        }
        activeOrders.add(order);
        
        //Add processed order to cart
        Cart customerCart = customerService.addOrdertoCart(customerService.getCurrentCustomer().getId(), order);
        
        //Store material name in a map for easy lookup
        Map<Long, String> materialMap = new HashMap<>();
        String matName = materialRepository.findById(order.getMatId()).get().getName();
        materialMap.put(order.getMatId(), matName);
        
        //Add necessary model attributes
        model.addAttribute("materialMap", materialMap);        
        model.addAttribute("cart", customerCart);
        objMap.put("cart", customerCart);

        return "order-success";
    }

    @GetMapping("/{id}/cart")
    @PreAuthorize("#customer.id == principal.id")
    public String cart(@PathVariable Long id,
    @ModelAttribute("customers") Customer customer, Model model,
    @ModelAttribute("cart") Cart cart) {
        List<OrderDTO> currentActiveOrders = new ArrayList<>();
        for(OrderDTO order:activeOrders) {
            if(order.getCustId() == id) {
                currentActiveOrders.add(order);
            }
        }
    
        model.addAttribute("activeOrders", activeOrders);
        orderMap.put("activeOrders", currentActiveOrders);        
        model.addAttribute("customers", customerService.getCurrentCustomer());
        return "cart";
    }

    @GetMapping("/{id}/checkout")
    @PreAuthorize("#customer.id == principal.id")
    public String checkout(@PathVariable Long id, Model model,
    @ModelAttribute("customers") Customer customer,
    @RequestParam(name = "paymentMethod", required = false) String payment, 
    @ModelAttribute("cart") Cart cart) {

        if (payment == null) {
            throw new InvalidOrderException("Order failed", "Please choose a valid payment method");
        }

        cart = (Cart) objMap.get("cart");
        cart.setPaymentMethod(payment);

        if (cart.getPaymentMethod() == null) {
            orderService.restoreOrder(activeOrders);
            throw new InvalidOrderException("Order failed: Payment method was not chosen", "Please choose a valid payment method");
        }

        //Add orders to invoice of current customer
        List<OrderDTO> invoiceOrders = new ArrayList<>();
        for(OrderDTO order : activeOrders) {
            order.setStatus("Order placed");
            order.setTimestamp(LocalDateTime.now());
            if (order.getCustId() == id) {
                invoiceOrders.add(order);
            }            
        }  
         // //Remove checked out orders from cart --> CURRENTLY CAUSING NoSuchElementException HERE AS THE PAGE IS RELOADED DUE TO IT BEING GETMAPPING
        // ListIterator<OrderDTO> iter = activeOrders.listIterator();
        // while (iter.hasNext()) {
        //     if(iter.next().getCustId() == id && iter.next().getStatus().equals("Order placed")) {
        //         iter.remove();
        //     }
        // }
        model.addAttribute("cart", cart);
        objMap.put("cart", cart);
        orderMap.put("invoiceOrders", invoiceOrders);
        return "checkout-success";
    }

    @GetMapping("/{id}/invoice")
    @PreAuthorize("#customer.id == principal.id")
    public String getLastInvoice(@PathVariable Long id,
    @ModelAttribute("customers") Customer customer, Model model,
    @ModelAttribute("cart") Cart cart) {

        List<OrderDTO> invoiceOrders = orderMap.get("invoiceOrders");
        cart = (Cart) objMap.get("cart");
        
        InvoiceDTO invoice = new InvoiceDTO(invoiceOrders);
        invoice.setPaymentMethod(cart.getPaymentMethod());
        model.addAttribute("invoice", invoice);
        
        return "invoice";
    }
   
}
