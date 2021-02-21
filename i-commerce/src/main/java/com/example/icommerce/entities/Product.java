package com.example.icommerce.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Product name is required.")
    @Column(name = "sku", unique = true, nullable = false, length = 12)
    private String sku;

    @NotEmpty(message = "Product name is required.")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductPrice> prices = new ArrayList<>();

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdDate;

    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant modifiedDate;

    @Column(name = "active")
    private boolean active;

    public Product () {
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
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

    public void addProductPrice (@NotNull ProductPrice productPrice) {
        this.prices.add(productPrice);
        productPrice.setProduct(this);
    }

    public List<ProductPrice> getPrices () {
        return prices;
    }

    public void setPrices (List<ProductPrice> prices) {
        this.prices = prices;
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

    public boolean isActive () {
        return active;
    }

    public void setActive (boolean active) {
        this.active = active;
    }

    public String getSku () {
        return sku;
    }

    public void setSku (String sku) {
        this.sku = sku;
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

    @Override
    public String toString () {
        return "Product{" +
               "id=" + id +
               ", sku='" + sku + '\'' +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", brand='" + brand + '\'' +
               ", color='" + color + '\'' +
               ", createdDate=" + createdDate +
               ", modifiedDate=" + modifiedDate +
               ", active=" + active +
               '}';
    }

    @Override
    public boolean equals (Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !(o instanceof Product) ) {
            return false;
        }
        Product product = (Product) o;
        return active == product.active &&
               Objects.equals(id, product.id) &&
               Objects.equals(sku, product.sku) &&
               Objects.equals(name, product.name) &&
               Objects.equals(description, product.description) &&
               Objects.equals(brand, product.brand) &&
               Objects.equals(color, product.color) &&
               Objects.equals(prices, product.prices) &&
               Objects.equals(createdDate, product.createdDate) &&
               Objects.equals(modifiedDate, product.modifiedDate);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, sku, name, description, brand, color, prices, createdDate, modifiedDate, active);
    }
}
