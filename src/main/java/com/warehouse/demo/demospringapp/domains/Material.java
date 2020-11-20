package com.warehouse.demo.demospringapp.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

// import com.warehouse.demo.demospringapp.domains.Supplier;


@Entity
public class Material {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="NAME")
    private String name;
    @Column(name="DETAILS")
    String details;
    @Column(name="UNITS")
    private Long units;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "matsList", fetch = FetchType.LAZY)
    private List<Supplier> suppList = new ArrayList<>();

    public Material() {
        super();
    }

    public Long getUnits() {
        return units;
    }

    public Long getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(final String details) {
        this.details = details;
    }

    public List<Supplier> getSuppList() {
        return suppList;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUnits(final Long units){
        this.units = units;
    }

    public void setSuppList(List<Supplier> suppList) {
        this.suppList = suppList;
    }

    @Override
    public String toString() {
        return "Material { id = " + id + " name = " + name + " details = " + details + " }";
    }

    public Material(String name, String details, long units) {
        this.name = name;
        this.details = details;
        this.units = units;
    }
    
}
