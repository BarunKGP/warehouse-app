package com.warehouse.demo.demospringapp.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.warehouse.demo.demospringapp.auth.AuthGroup;
import com.warehouse.demo.demospringapp.auth.AuthGroupRepository;

@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "SUPP_MAT", 
    joinColumns = { @JoinColumn(name = "SUPP_ID") }, 
    inverseJoinColumns = {@JoinColumn(name = "MAT_ID") })
    private List<Material> matsList = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
    orphanRemoval = true)
    @JoinColumn(name = "auth_group")
    private AuthGroup authGroup;

    public AuthGroup getAuthGroup() {
        return authGroup;
    }

    public long getId() {
        return id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public List<Material> getMatsList() {
        return matsList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId (long id) {
        this.id = id;
    }

    public void setMatsList(List<Material> matsList) {
        this.matsList = matsList;
    }

    public void setAuthGroup (AuthGroup authGroup) {
        this.authGroup = authGroup;
    }

    public void addMaterial (Material material) {
        this.matsList.add(material);
        material.getSuppList().add(this);
    }

    public void removeMaterial (Material material) {
        matsList.remove(material);
        material.getSuppList().remove(this);
    }

    public void addAuthGroup() {
        AuthGroup authGroup = new AuthGroup(this.getUsername(), "SUPPLIER");
        this.setAuthGroup(authGroup);
    }

    public void removeAuthGroup(AuthGroupRepository authGroupRepository) {
        // long auth_id = this.getAuthGroup().getId();
        // AuthGroup auth = authGroupRepository.findById(id).get();
        // authGroupRepository.deleteById(id);
        this.getAuthGroup().setUsername(null);
        this.getAuthGroup().setAuthrole(null);
        authGroupRepository.delete(this.getAuthGroup());

    }

    @Override
    public String toString() {
        return "Supplier {id = " + id
        + ", name = " + firstname + " " + lastname
        + ", email = " + email + "}"; 
    }

    public Supplier() {
        super();
    }

    public Supplier(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = email;
        this.password = password;
    }

        

    
}
