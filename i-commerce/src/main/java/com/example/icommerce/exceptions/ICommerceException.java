package com.example.icommerce.exceptions;

import com.example.icommerce.constants.ErrorCode;

public class ICommerceException extends Exception {
    static final  long serialVersionUID = 1L;
    private final int  errorCode;

    public ICommerceException () {
        this.errorCode = ErrorCode.INVALID_REQUEST;
    }

    public ICommerceException (String message) {
        this(ErrorCode.INVALID_REQUEST, message);
    }

    public ICommerceException (String message, Throwable throwable) {
        this(ErrorCode.INVALID_REQUEST, message, throwable);
    }

    public ICommerceException (Throwable throwable) {
        this(ErrorCode.INVALID_REQUEST, throwable);
    }

    public ICommerceException (int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ICommerceException (int errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public ICommerceException (int errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
    }

    public int getErrorCode () {
        return errorCode;
    }
}
