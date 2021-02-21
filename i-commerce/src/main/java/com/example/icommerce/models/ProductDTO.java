package com.example.icommerce.models;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProductDTO {

    private String sku;
    private String name;
    private String description;
    private String brand;
    private String color;
    private double price;
    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant modifiedDate;

    public String getSku () {
        return sku;
    }

    public void setSku (String sku) {
        this.sku = sku;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getBrand () {
        return brand;
    }

    public void setBrand (String brand) {
        this.brand = brand;
    }

    public String getColor () {
        return color;
    }

    public void setColor (String color) {
        this.color = color;
    }

    public double getPrice () {
        return price;
    }

    public void setPrice (double price) {
        this.price = price;
    }

    public boolean isActive () {
        return active;
    }

    public void setActive (boolean active) {
        this.active = active;
    }

    public Instant getCreatedDate () {
        return createdDate;
    }

    public void setCreatedDate (Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getModifiedDate () {
        return modifiedDate;
    }

    public void setModifiedDate (Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
