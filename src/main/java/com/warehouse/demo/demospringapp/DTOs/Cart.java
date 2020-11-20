package com.warehouse.demo.demospringapp.DTOs;

import java.util.ArrayList;
import java.util.List;

import com.warehouse.demo.demospringapp.error.EntityNotFoundException;

public class Cart {

    private Long custId;
    private List<OrderDTO> orders = new ArrayList<>();
    private String paymentMethod;

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
    
    public void addOrder(OrderDTO order) {
        this.orders.add(order);
    }

    public void removeOrder(OrderDTO order){
        if(this.orders.contains(order)) {
            this.orders.remove(order);
        }
        else{
            throw new EntityNotFoundException("Order does not exist in cart");
        }
    }

    public Cart() {
    }

    public Cart(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public Cart(List<OrderDTO> orders, String paymentMethod) {
        this.orders = orders;
        this.paymentMethod = paymentMethod;
    }

    public Cart(Long custId, List<OrderDTO> orders, String paymentMethod) {
        this.custId = custId;
        this.orders = orders;
        this.paymentMethod = paymentMethod;
    }

    public Cart(Long custId, List<OrderDTO> orders) {
        this.custId = custId;
        this.orders = orders;
    }

    public Cart(Long custId) {
        this.custId = custId;
    }
}
