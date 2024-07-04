package ru.nand.SellMyProduct.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
//@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    private int id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    //@Column(name = "name")
    private String name;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password id mandatory")
    private String password;

    @Column(name = "auth_code")
    private String authCode;

    private String personRole;



    public void setPersonRole(String personRole) {
        this.personRole = personRole;
    }

    @Column(name = "is_authenticated", nullable = false, columnDefinition = "boolean default false")
    private boolean isAuthenticated;


    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Person() {
    }

    public Person(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Person(String name, String email, String password, String authCode, String personRole, boolean isAuthenticated, List<Product> products) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.authCode = authCode;
        this.personRole = personRole;
        this.isAuthenticated = isAuthenticated;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getPersonRole() {
        return personRole;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
