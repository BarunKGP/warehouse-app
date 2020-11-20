package com.warehouse.demo.demospringapp.DTOs;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceDTO {
    private String id;
    private List<OrderDTO> orders;
    private String paymentMethod;
    private LocalDateTime timestamp;
    private static Integer count = 1;

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public void generateId() {
        OrderDTO order = orders.get(0);
        String genId = Long.toString(order.getCustId()) + "_" + Integer.toString(count);
        count++;
        this.setId(genId);
        this.timestamp = order.getTimestamp();
    }        

    public InvoiceDTO(List<OrderDTO> orders) {
        this.orders = orders;
        generateId();
    }
    
}
