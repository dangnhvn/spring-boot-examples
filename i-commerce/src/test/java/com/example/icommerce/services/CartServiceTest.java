package com.example.icommerce.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.hibernate.internal.util.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceTest extends BaseServiceTest {

    @Autowired
    private CartService cartService;

    @BeforeEach
    public void beforeEach() {
        cartService.clearOrder();
    }

    @Test
    public void testAddProduct () {
        String sku = "ICMAIP000010";
        cartService.addProduct(sku);

        Assertions.assertEquals(1, cartService.getOrdersInCart().size());
    }

    @Test
    public void testAddProductNotFound () {
        String sku = "122312";
        cartService.addProduct(sku);

        Assertions.assertEquals(0, cartService.getOrdersInCart().size());
    }

    @Test
    public void testAddMultipleProducts () {
        addTestData();

        Assertions.assertEquals(4, cartService.getOrdersInCart().size());
    }

    @Test
    public void testAddMultipleProductsWithSameKey () {
        addTestData();
        String sku = "ICMAIP000010";
        cartService.addProduct(sku);

        Assertions.assertEquals(4, cartService.getOrdersInCart().size());
        Assertions.assertEquals(2, cartService.getProductSkusInOrder().get(sku).intValue());
    }

    @Test
    public void testRemoveProductNotFound() {
        List<String> skus = Arrays.asList("ICMAIP000010");

        for (String sku : skus ) {
            cartService.addProduct(sku);
        }

        Assertions.assertEquals(1, cartService.getOrdersInCart().size());

        cartService.removeProduct("123454647");
        Assertions.assertEquals(1, cartService.getOrdersInCart().size());
    }

    @Test
    public void testRemoveProduct () {

        addTestData();
        String sku = "ICMAIP000010";
        cartService.addProduct(sku);

        Assertions.assertEquals(4, cartService.getOrdersInCart().size());
        Assertions.assertEquals(2, cartService.getProductSkusInOrder().get(sku).intValue());

        cartService.removeProduct(sku);
        Assertions.assertEquals(4, cartService.getOrdersInCart().size());

        cartService.removeProduct(sku);
        Assertions.assertEquals(3, cartService.getOrdersInCart().size());
    }

    @Test
    public void testCheckoutWithUserNull () {
        addTestData();
        Assertions.assertEquals(4, cartService.getOrdersInCart().size());

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                                                                     () -> cartService.checkout(null));

        Assertions.assertEquals("'username' parameter is empty", exception.getMessage());

    }

    @Test
    public void testCheckoutWithUserNotFound () {
        String username = "username";
        addTestData();
        Assertions.assertEquals(4, cartService.getOrdersInCart().size());

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                                                                     () -> cartService.checkout(username));

        Assertions.assertEquals("User '" + username + "' not found", exception.getMessage());

    }

    @Test
    public void testCheckout () {
        addTestData();
        Assertions.assertEquals(4, cartService.getOrdersInCart().size());

        String orderNumber = cartService.checkout("user");
        Assertions.assertTrue(StringHelper.isNotEmpty(orderNumber));
        Assertions.assertEquals(0, cartService.getOrdersInCart().size());

    }

    @Test
    public void testGetTotalPrice () {
        addTestData();
        Assertions.assertEquals(4, cartService.getOrdersInCart().size());

        Assertions.assertNotEquals(BigDecimal.ZERO, cartService.getTotalPrice());

    }

    private void addTestData() {
        List<String> skus = Arrays.asList("ICMAIP000010", "ICMAIP000011", "ICMAIP000012", "ICMAIP000013");

        for (String sku : skus ) {
            cartService.addProduct(sku);
        }
    }


}
