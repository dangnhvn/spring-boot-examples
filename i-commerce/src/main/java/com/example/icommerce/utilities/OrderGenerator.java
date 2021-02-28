package com.example.icommerce.utilities;

public final class OrderGenerator {
    private static final String DEFAULT_PREFIX_VALUE = "ICM";
    private static       long   value                = 1;

    private OrderGenerator () {
    }

    public static String generate () {
        if ( value > 100000000 ) {
            reset();
        }
        String generated = DEFAULT_PREFIX_VALUE + String.format("%09d", value);
        value++;

        return generated;
    }

    public static void reset () {
        value = 1;
    }
}
