package com.example.icommerce.exceptions;

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 5741851702605182177L;

    public AuthenticationException (String msg) {
        super(msg);
    }

    public AuthenticationException (String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
