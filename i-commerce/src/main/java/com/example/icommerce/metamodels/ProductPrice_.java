package com.example.icommerce.metamodels;

import java.time.Instant;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.ProductPrice;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductPrice.class)
public abstract class ProductPrice_ {

	public static volatile SingularAttribute<ProductPrice, Product> product;
	public static volatile SingularAttribute<ProductPrice, Double>  price;
	public static volatile SingularAttribute<ProductPrice, Instant> modifiedDate;
	public static volatile SingularAttribute<ProductPrice, Long>    id;

	public static final String PRODUCT = "product";
	public static final String PRICE = "price";
	public static final String MODIFIED_DATE = "modifiedDate";
	public static final String ID = "id";

}

