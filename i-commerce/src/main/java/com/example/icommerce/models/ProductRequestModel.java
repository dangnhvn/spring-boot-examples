package com.example.icommerce.models;

import javax.validation.constraints.NotEmpty;

public class ProductRequestModel {

    private String productSku;

    @NotEmpty(message = "'name' attribute must contain value")
    private String name;

    private String description;

    private Double price;

    private String brand;

    private String color;

    public String getProductSku () {
        return productSku;
    }

    public void setProductSku (String productSku) {
        this.productSku = productSku;
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

    public Double getPrice () {
        return price;
    }

    public void setPrice (Double price) {
        this.price = price;
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
}
