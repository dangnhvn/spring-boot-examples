package com.example.icommerce.metamodels;

import java.time.Instant;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.ProductPrice;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

    public static volatile SingularAttribute<Product, Instant>  createdDate;
    public static volatile SingularAttribute<Product, String>   color;
    public static volatile SingularAttribute<Product, String>   name;
    public static volatile SingularAttribute<Product, Instant>  modifiedDate;
    public static volatile SingularAttribute<Product, String>   description;
    public static volatile SingularAttribute<Product, Boolean>  active;
    public static volatile SingularAttribute<Product, Long>     id;
    public static volatile SingularAttribute<Product, String>   sku;
    public static volatile ListAttribute<Product, ProductPrice> prices;
    public static volatile SingularAttribute<Product, String>   brand;

    public static final String CREATED_DATE  = "createdDate";
    public static final String COLOR         = "color";
    public static final String NAME          = "name";
    public static final String MODIFIED_DATE = "modifiedDate";
    public static final String DESCRIPTION   = "description";
    public static final String ACTIVE        = "active";
    public static final String ID            = "id";
    public static final String SKU           = "sku";
    public static final String PRICES        = "prices";
    public static final String BRAND         = "brand";

}
