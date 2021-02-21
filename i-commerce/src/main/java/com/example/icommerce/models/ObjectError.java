package com.example.icommerce.models;

import java.io.Serializable;

public class ObjectError implements Serializable {

    private static final long serialVersionUID = 5346643051890911561L;

    private String message;
    private int    code;

    public ObjectError () {

    }

    public ObjectError (int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public int getCode () {
        return code;
    }

    public void setCode (int code) {
        this.code = code;
    }
}
