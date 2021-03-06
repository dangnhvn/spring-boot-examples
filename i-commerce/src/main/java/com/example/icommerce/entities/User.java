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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "active")
    private boolean active;

    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant createdDate;

    @Column(name = "modified_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant modifiedDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserActivity> customerActivities = new ArrayList<>();

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Order> orders = new ArrayList<>();

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
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

    @JsonIgnore
    public List<UserActivity> getCustomerActivities () {
        return customerActivities;
    }

    public void setCustomerActivities (List<UserActivity> customerActivities) {
        this.customerActivities = customerActivities;
    }

    @Transient
    public void addActivity (UserActivity customerActivity) {
        this.customerActivities.add(customerActivity);
        customerActivity.setUser(this);
    }

//    @Transient
//    public void addOrder (Order order) {
//        order.setUser(this);
//        this.getOrders().add(order);
//    }
//
//    @JsonIgnore
//    public List<Order> getOrders () {
//        return orders;
//    }
//
//    public void setOrders (List<Order> orders) {
//        this.orders = orders;
//    }

    @Override
    public boolean equals (Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        User user = (User) o;
        return active == user.active &&
               Objects.equals(id, user.id) &&
               Objects.equals(username, user.username) &&
               Objects.equals(password, user.password) &&
               Objects.equals(createdDate, user.createdDate) &&
               Objects.equals(modifiedDate, user.modifiedDate) &&
               Objects.equals(customerActivities, user.customerActivities);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, username, password, active, createdDate, modifiedDate, customerActivities);
    }
}
