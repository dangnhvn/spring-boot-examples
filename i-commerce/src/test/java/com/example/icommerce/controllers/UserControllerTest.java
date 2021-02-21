package com.example.icommerce.controllers;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.icommerce.models.ProductRequestModel;
import com.example.icommerce.models.Response;
import com.example.icommerce.models.UserRequestModel;

public class UserControllerTest extends BaseControllerTest {

    @Override
    protected String getEndPoint () {
        return "/users";
    }

    @Test
    public void testCreateUser() {
        UserRequestModel model = new UserRequestModel();
        model.setUsername("abc");
        model.setPassword("pass");

        HttpEntity<UserRequestModel> httpEntity = new HttpEntity<>(model, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.postForEntity(getUrl(), httpEntity, Response.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());

        Map response = (Map) responseEntity.getBody().getData();
        Assertions.assertEquals("abc", response.get("username").toString());
        Assertions.assertEquals(Boolean.TRUE.toString(), response.get("active").toString());
    }
}
