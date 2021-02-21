package com.example.icommerce.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 984871899665773889L;

    private T           data;
    private ObjectError error;

    public Response () {
    }

    public Response (T data) {
        this.data = data;
    }

    public Response (ObjectError error) {
        this.error = error;
    }

    public T getData () {
        return data;
    }

    public void setData (T data) {
        this.data = data;
    }

    public ObjectError getError () {
        return error;
    }

    public void setError (ObjectError error) {
        this.error = error;
    }

}
