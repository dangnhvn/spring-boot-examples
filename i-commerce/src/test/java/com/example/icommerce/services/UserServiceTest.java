package com.example.icommerce.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.icommerce.entities.CustomerActivity;
import com.example.icommerce.entities.User;
import com.example.icommerce.models.UserRequestModel;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByUsername () {
        String username = "admin";

        User user = userService.getUserByUsername(username);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertTrue(user.isActive());

    }

    @Test
    public void testCreateUserWithNullPointerException () {
        UserRequestModel model = null;

        NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> userService.createUser(model));
        Assertions.assertEquals("UserRequestModel is null", exception.getMessage());

    }

    @Test
    public void testCreateUserWithIllegalArgumentException () {
        UserRequestModel model = new UserRequestModel();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> userService.createUser(model));
        Assertions.assertEquals("'username' attribute must contain value", exception.getMessage());

    }

    @Test
    public void testCreateUser () {
        UserRequestModel model = new UserRequestModel();
        model.setUsername("abc");
        model.setPassword("pass");

        User user = userService.createUser(model);
        Assertions.assertNotNull(user);
        Assertions.assertNotEquals(0, user.getId());
        Assertions.assertEquals("abc", user.getUsername());
        Assertions.assertEquals("pass", user.getPassword());

    }

    @Test
    public void testCreateCustomerActivity () {
        String username = "user";
        String api      = "api";
        String method   = "method";
        String query    = "query";

        CustomerActivity activity = userService.createCustomerActivity(username, api, method, query);
        Assertions.assertNotNull(activity);
        Assertions.assertNotEquals(Long.valueOf(0), activity.getId());

    }

    @Test
    public void testGetAllActivities () {
        String username = "user";
        String api      = "api";
        String method   = "method";
        String query    = "query";

        for (int i = 0; i < 10; i++) {
            CustomerActivity activity = userService.createCustomerActivity(username, api, method, query);
            Assertions.assertNotNull(activity);
            Assertions.assertNotEquals(Long.valueOf(0), activity.getId());
        }

        Assertions.assertEquals(10, userService.getAllActivities(username).size());

    }

}
