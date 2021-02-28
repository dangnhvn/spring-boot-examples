package com.example.icommerce.services;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.example.icommerce.dtos.OrderDTO;
import com.example.icommerce.entities.Order;
import com.example.icommerce.entities.User;
import com.example.icommerce.models.PagingRequest;

public class OrderServiceTest extends BaseServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateOrderWithUsernameIsEmpty () {
        User user = new User();
        user.setUsername(null);

        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> orderService.createOrder(user, null, ""));

        Assertions.assertEquals("'username' parameter is empty", exception.getMessage());
    }

    @Test
    public void testCreateOrderSuccessfully () {
        User user = userService.getUserByUsername("test");
        Map<String, Integer> orderDetails = new HashMap<>();
        orderDetails.put("ICMAIP000009", Integer.valueOf(10));

        Order order = orderService.createOrder(user, orderDetails, "");
        Assertions.assertNotNull(order.getId());
    }

    @Test
    public void testGetOrdersByUsername () {
        String username = "test";
        User user = userService.getUserByUsername(username);
        Map<String, Integer> orderDetails = new HashMap<>();
        orderDetails.put("ICMAIP000009", Integer.valueOf(10));

        Order order = orderService.createOrder(user, orderDetails, "");
        Assertions.assertNotNull(order);
        Assertions.assertNotNull(order.getId());

        Page<OrderDTO> orders = orderService.getOrdersByUsername(username, new PagingRequest());
        Assertions.assertNotNull(orders);
        Assertions.assertEquals(2, orders.getTotalElements());
        Assertions.assertEquals(order.getOrderNumber(), orders.getContent().get(1).getOrderNumber());

    }

    @Test
    public void testGetOrderByOrderNumberNull () {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> orderService.getOrder(null));

        Assertions.assertEquals("Order number is missing", exception.getMessage());

    }

    @Test
    public void testGetOrderByOrderNumber () {

        User user = userService.getUserByUsername("test");
        Map<String, Integer> orderDetails = new HashMap<>();
        orderDetails.put("ICMAIP000009", Integer.valueOf(10));

        Order order = orderService.createOrder(user, orderDetails, "");
        Assertions.assertNotNull(order.getId());

        String orderNumber = order.getOrderNumber();

        OrderDTO orderDTO = orderService.getOrderDetailDTO(orderNumber);
        Assertions.assertNotNull(orderDTO);
        Assertions.assertEquals(1, orderDTO.getDetails().size());
        Assertions.assertEquals(10, orderDTO.getDetails().get(0).getQuantity());

    }
}
