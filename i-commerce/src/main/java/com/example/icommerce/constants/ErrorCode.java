package com.example.icommerce.constants;

public final class ErrorCode {

    private ErrorCode () {
        throw new IllegalArgumentException("Constants class");
    }

    public static final int INVALID_REQUEST = 9001;
    public static final int INVALID_TOKEN   = 9002;
    public static final int CREATION_ERROR  = 9003;
    public static final int SERVER_ERROR    = 9999;
}
