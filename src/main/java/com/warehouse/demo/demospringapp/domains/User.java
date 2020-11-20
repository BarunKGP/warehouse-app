package com.warehouse.demo.demospringapp.domains;

public class User {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private User_Type type;

    public String getPassword() {
        return password;
    }

    public User_Type getType() {
        return type;
    }

    public void setType(User_Type type) {
        this.type = type;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(String firstname, String lastname, String email, String password, User_Type type) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    
}
