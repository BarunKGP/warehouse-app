package com.warehouse.demo.demospringapp.services;

import java.util.List;

import com.warehouse.demo.demospringapp.DTOs.OrderDTO;
import com.warehouse.demo.demospringapp.domains.Material;
import com.warehouse.demo.demospringapp.error.InvalidOrderException;
import com.warehouse.demo.demospringapp.repos.MaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDTOService {

    @Autowired
    private MaterialRepository materialRepository;
        
    private OrderDTO convertToOrderDTO (Long custId, Long matId, Long orderedUnits) {
        OrderDTO order = new OrderDTO(custId, matId, orderedUnits);
        return order;
    }

    public OrderDTO validateOrder (OrderDTO order) {
        Long matId = order.getMatId();
        Long orderedUnits = order.getUnits();
        if(materialRepository.findById(matId).isPresent()) {
            if(orderedUnits <= materialRepository.findById(matId).get().getUnits()) {
                return order;
            }
            throw new InvalidOrderException("Order failed: not enough stock", "Please order fewer units");
        }
        throw new InvalidOrderException("Order failed: material with id:" + matId + " does not exist", "Please choose an available material");
    }
    
    public OrderDTO processOrder(Long custId, Long matId, Long orderedUnits) {
        OrderDTO order = convertToOrderDTO(custId, matId, orderedUnits);
        order = validateOrder(order);
        Material material = materialRepository.findById(order.getMatId()).get();
        Long processedUnits = material.getUnits() - orderedUnits;
        material.setUnits(processedUnits);
        materialRepository.save(material);
        order.setStatus("Added to cart");
        return order;

        //ADD LOGIC FOR REMOVING ZERO UNIT MATERIALS
    }

    public void restoreOrder (List<OrderDTO> failedOrders) {
        for (OrderDTO order : failedOrders) {
            Long units = order.getUnits();
            Material material = materialRepository.findById(order.getMatId()).get();
            Long processedUnits = material.getUnits() + units;
            material.setUnits(processedUnits);
            materialRepository.save(material);
            order.setStatus("Failed");
        }
    }
}
