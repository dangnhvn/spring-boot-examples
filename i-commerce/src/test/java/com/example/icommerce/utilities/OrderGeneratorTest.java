package com.example.icommerce.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderGeneratorTest {

    @BeforeEach
    public void beforeEach () {
        OrderGenerator.reset();
    }

    @Test
    public void testGenerate () {
        String expected = "ICM000000001";
        Assertions.assertEquals(expected, OrderGenerator.generate());
    }
}
