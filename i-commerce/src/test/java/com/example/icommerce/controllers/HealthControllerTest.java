package com.example.icommerce.controllers;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.icommerce.models.Response;

public class HealthControllerTest extends BaseControllerTest {

    @Override
    protected String getEndPoint () {
        return "/health";
    }

    @Test
    public void getHealthCheck() {
        ResponseEntity<Response> responseEntity = restTemplate.getForEntity(getUrl(), Response.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNull(responseEntity.getBody().getError());

        Map response = (Map) responseEntity.getBody().getData();
        Assertions.assertEquals("UP", response.get("status").toString());

    }
}
