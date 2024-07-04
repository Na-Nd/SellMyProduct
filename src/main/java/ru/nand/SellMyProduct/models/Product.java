package ru.nand.SellMyProduct.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Title is mondatory")
    private String title;

    private String imagePath;

    @Column(columnDefinition = "TEXT")
    private String description;

    private double price;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Product(String title, String imagePath, String description, double price) {
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
    }

    public Product(String title, String imagePath, String description, double price, Person person) {
        this.title = title;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.person = person;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
