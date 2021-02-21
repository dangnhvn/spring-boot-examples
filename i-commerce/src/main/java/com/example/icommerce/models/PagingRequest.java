package com.example.icommerce.models;

import java.io.Serializable;
import java.util.Optional;

public class PagingRequest implements Serializable {

    private static final long serialVersionUID = -6209972368913919977L;

    private Integer page;
    private Integer limit;
    private String  sortBy;
    private String  name;
    private String  brand;
    private Double  price;
    private String  color;
    private String productSku;

    public PagingRequest () {
    }

    public Integer getPage () {
        return Optional.ofNullable(page).orElse(Integer.valueOf(0));
    }

    public void setPage (Integer page) {
        this.page = page;
    }

    public Integer getLimit () {
        return Optional.ofNullable(limit).orElse(Integer.valueOf(20));
    }

    public void setLimit (Integer limit) {
        this.limit = limit;
    }

    public String getSortBy () {
        return sortBy;
    }

    public void setSortBy (String sortBy) {
        this.sortBy = sortBy;
    }

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

    @Override
    public String toString () {
        return "page=" + page +
               "&limit=" + limit +
               "&sortBy='" + sortBy + '\'' +
               "&name='" + name + '\'' +
               "&brand='" + brand + '\'' +
               "&price=" + price +
               "&color='" + color + '\'' +
               "&productSku='" + productSku + '\'';
    }
}
