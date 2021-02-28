package com.example.icommerce.models;

public class ProductPagingRequest extends PagingRequest {

    private String name;
    private String brand;
    private Double price;
    private String color;
    private String productSku;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getBrand () {
        return brand;
    }

    public void setBrand (String brand) {
        this.brand = brand;
    }

    public Double getPrice () {
        return price;
    }

    public void setPrice (Double price) {
        this.price = price;
    }

    public String getColor () {
        return color;
    }

    public void setColor (String color) {
        this.color = color;
    }

    public String getProductSku () {
        return productSku;
    }

    public void setProductSku (String productSku) {
        this.productSku = productSku;
    }
}
