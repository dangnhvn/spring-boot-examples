package com.example.icommerce.utilities;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SkuGeneratorTest {

    @BeforeEach
    public void beforeEach() {
        SkuGenerator.reset();
    }

    @Test
    public void testGenerateWithDefault() {
        String expected = "ICM000000001";
        Assertions.assertEquals(expected, SkuGenerator.generate());
    }

    @Test
    public void testGenerateWithPrefixLengthGreaterThanThree() {
        String expected = "ICMNUL000001";
        Assertions.assertEquals(expected, SkuGenerator.generate("null"));
    }

    @Test
    public void testGenerateWithPrefixLengthLesserThanThree() {
        String expected = "ICMNU0000001";
        Assertions.assertEquals(expected, SkuGenerator.generate("nu"));
    }

}