package com.example.icommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.icommerce.utilities.SkuGenerator;

@SpringBootTest
@ActiveProfiles("test")
class ICommerceApplicationTests {

    @BeforeEach
    public void beforeEach() {
        SkuGenerator.reset();
    }

    @Test
    void contextLoads () {
    }

}
