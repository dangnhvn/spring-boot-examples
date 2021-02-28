package com.example.icommerce.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "order_details")
public class OrderDetail implements Serializable {

    @EmbeddedId
    private OrderDetailPK pk;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @JsonIgnore
    public Product getProduct () {
        return pk.getProduct();
    }

    @Transient
    public Double getTotalPrice () {
        double sum = 0;

        if ( getProduct() != null ) {
            ProductPrice productPrice = getProduct().getPrices().stream().min((t0, t1) -> t1.getModifiedDate().compareTo(t0.getModifiedDate())).orElse(null);
            if ( productPrice != null ) {
                sum = sum + (productPrice.getPrice() * quantity);
            }
        }

        return sum;
    }
}
