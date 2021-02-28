package com.example.icommerce.controllers;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.icommerce.models.Response;

public class OrderControllerTest extends BaseControllerTest {

    @Override
    protected String getEndPoint () {
        return "/orders";
    }

    @Test
    public void testGetOrderByUsername () {
        String username = "user";

        HttpEntity httpEntity = new HttpEntity(null, getDefaultHeader());

        ResponseEntity<Response> responseEntity = restTemplate.exchange(getUrl(), HttpMethod.GET, httpEntity, Response.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Assertions.assertTrue(responseEntity.getBody().getData() instanceof List);
        Assertions.assertTrue(((List) responseEntity.getBody().getData()).isEmpty());
    }

}
