package com.example.icommerce.constants;

public final class Constants {

    private Constants () {
        throw new IllegalArgumentException("Constants class");
    }

    public static final String REQUEST_IDENTITY_ID_HEADER = "X-Test-IdentityID";
    public static final String REQUEST_ID_HEADER          = "X-Test-RequestID";
}
