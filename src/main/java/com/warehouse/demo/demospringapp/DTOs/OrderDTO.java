package com.warehouse.demo.demospringapp.DTOs;

import java.time.LocalDateTime;

public class OrderDTO {
    private Long matId;
    private Long custId;
    private Long units;
    private String status;
    private LocalDateTime timestamp;

    public Long getUnits() {
        return units;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMatId() {
        return matId;
    }

    public void setMatId(Long matId) {
        this.matId = matId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public void setUnits(Long units) {
        this.units = units;
    }

    public OrderDTO(Long custId, Long matId, Long units) {
        this.matId = matId;
        this.custId = custId;
        this.units = units;
    }

    public OrderDTO() {
        this.status = "Unassigned";
        this.timestamp = LocalDateTime.MIN;
    }

    public OrderDTO(Long matId, Long custId, Long units, String status) {
        this.matId = matId;
        this.custId = custId;
        this.units = units;
        this.status = status;
        this.status = "Unassigned";
        this.timestamp = LocalDateTime.MIN;
    }

    @Override
    public String toString() {
        return ("customer id: " + custId + 
        ", material id: " + matId + 
        ", units ordered: " + units +
        ", status: " + status + 
        ", timestamp: " + timestamp);
    }
    
}
