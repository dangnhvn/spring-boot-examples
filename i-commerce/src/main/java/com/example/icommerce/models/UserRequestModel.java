package com.example.icommerce.models;

import javax.validation.constraints.NotEmpty;

public class UserRequestModel {

    @NotEmpty(message = "'name' attribute must contain value")
    private String username;

    @NotEmpty(message = "'name' attribute must contain value")
    private String password;

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
}
