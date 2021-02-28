package com.example.icommerce.entities;

import java.io.Serializable;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_number", nullable = false)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name = "status")
    private String status;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdDate;

    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant modifiedDate;

    @OneToMany(mappedBy = "pk.order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getOrderNumber () {
        return orderNumber;
    }

    public void setOrderNumber (String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @JsonIgnore
    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
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

    public List<OrderDetail> getOrderDetailList () {
        return orderDetailList;
    }

    public void setOrderDetailList (List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Transient
    public Double getTotalOrderPrice () {
        double sum = 0D;
        for (OrderDetail op : orderDetailList) {
            sum += op.getTotalPrice();
        }
        return sum;
    }

    @Override
    public String toString () {
        return "Order{" +
               "id=" + id +
               ", user=" + user +
               ", status='" + status + '\'' +
               ", createdDate=" + createdDate +
               ", modifiedDate=" + modifiedDate +
               ", orderDetailList=" + orderDetailList +
               '}';
    }

    @Override
    public boolean equals (Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
               Objects.equals(orderNumber, order.orderNumber) &&
               Objects.equals(user, order.user) &&
               Objects.equals(status, order.status) &&
               Objects.equals(createdDate, order.createdDate) &&
               Objects.equals(modifiedDate, order.modifiedDate) &&
               Objects.equals(orderDetailList, order.orderDetailList);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, orderNumber, user, status, createdDate, modifiedDate, orderDetailList);
    }

    public void addOrderDetail (OrderDetail orderDetail) {
        this.orderDetailList.add(orderDetail);
    }
}
