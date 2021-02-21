package com.example.icommerce.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceTest extends BaseServiceTest {

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        orderService.clearOrder();
    }

    @Test
    public void testAddProduct () {
        String sku = "ICMAIP000010";
        orderService.addProduct(sku);

        Assertions.assertEquals(1, orderService.getProductsInOrder().size());
    }

    @Test
    public void testAddProductNotFound () {
        String sku = "122312";
        orderService.addProduct(sku);

        Assertions.assertEquals(0, orderService.getProductsInOrder().size());
    }

    @Test
    public void testAddMultipleProducts () {
        addTestData();

        Assertions.assertEquals(4, orderService.getProductsInOrder().size());
    }

    @Test
    public void testAddMultipleProductsWithSameKey () {
        addTestData();
        String sku = "ICMAIP000010";
        orderService.addProduct(sku);

        Assertions.assertEquals(4, orderService.getProductsInOrder().size());
        Assertions.assertEquals(2, orderService.getProductSkusInOrder().get(sku).intValue());
    }

    @Test
    public void testRemoveProductNotFound() {
        List<String> skus = Arrays.asList("ICMAIP000010");

        for (String sku : skus ) {
            orderService.addProduct(sku);
        }

        Assertions.assertEquals(1, orderService.getProductsInOrder().size());

        orderService.removeProduct("123454647");
        Assertions.assertEquals(1, orderService.getProductsInOrder().size());
    }

    @Test
    public void testRemoveProduct () {

        addTestData();
        String sku = "ICMAIP000010";
        orderService.addProduct(sku);

        Assertions.assertEquals(4, orderService.getProductsInOrder().size());
        Assertions.assertEquals(2, orderService.getProductSkusInOrder().get(sku).intValue());

        orderService.removeProduct(sku);
        Assertions.assertEquals(4, orderService.getProductsInOrder().size());

        orderService.removeProduct(sku);
        Assertions.assertEquals(3, orderService.getProductsInOrder().size());
    }

    @Test
    public void testCheckout () {
        addTestData();
        Assertions.assertEquals(4, orderService.getProductsInOrder().size());

        orderService.checkout();
        Assertions.assertEquals(0, orderService.getProductsInOrder().size());

    }

    @Test
    public void testGetTotalPrice () {
        addTestData();
        Assertions.assertEquals(4, orderService.getProductsInOrder().size());

        Assertions.assertNotEquals(BigDecimal.ZERO, orderService.getTotalPrice());

    }

    private void addTestData() {
        List<String> skus = Arrays.asList("ICMAIP000010", "ICMAIP000011", "ICMAIP000012", "ICMAIP000013");

        for (String sku : skus ) {
            orderService.addProduct(sku);
        }
    }


}
