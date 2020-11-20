package com.warehouse.demo.demospringapp.domains;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
// import javax.validation.constraints.NotBlank;
import javax.persistence.OneToOne;

import com.warehouse.demo.demospringapp.auth.AuthGroup;
import com.warehouse.demo.demospringapp.auth.AuthGroupRepository;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    // @NotBlank(message = "Last Name is mandatory")
    private String lastname;
    // @NotBlank(message = "Email id is mandatory")
    private String email;
    private String username;
    private String password;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "auth_group")
    private AuthGroup authGroup;

    public Customer() {
        super();
    }

    public AuthGroup getAuthGroup() {
        return authGroup;
    }

    public void setAuthGroup(AuthGroup authGroup) {
        this.authGroup = authGroup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void addAuthGroup() {
        AuthGroup authGroup = new AuthGroup(this.getUsername(), "CUSTOMER");
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
        return "Customer {id = " + id
        + ", name = " + firstname + " " + lastname
        + ", email = " + email + "}"; 
    }

    public Customer(String firstname, String lastname, String email, 
                    String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = email;
        this.password = password;
    }

	public boolean isPresent() {
		return false;
	}





    

    
}
