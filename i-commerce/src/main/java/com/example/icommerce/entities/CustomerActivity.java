package com.example.icommerce.entities;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer_activities")
public class CustomerActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(name = "api", nullable = false)
    private String api;

    @Column(name = "method", nullable = false)
    private String method;

    @Column(name = "query")
    private String query;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    @JsonIgnore
    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    public String getApi () {
        return api;
    }

    public void setApi (String api) {
        this.api = api;
    }

    public String getMethod () {
        return method;
    }

    public void setMethod (String method) {
        this.method = method;
    }

    public String getQuery () {
        return query;
    }

    public void setQuery (String query) {
        this.query = query;
    }

    @Override
    public boolean equals (Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !(o instanceof CustomerActivity) ) {
            return false;
        }
        CustomerActivity that = (CustomerActivity) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(user, that.user) &&
               Objects.equals(api, that.api) &&
               Objects.equals(query, that.query);
    }

    @Override
    public int hashCode () {
        return Objects.hash(id, user, api, query);
    }
}
