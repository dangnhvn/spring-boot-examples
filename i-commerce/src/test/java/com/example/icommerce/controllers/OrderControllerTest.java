package com.example.icommerce.controllers;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.icommerce.constants.ErrorCode;
import com.example.icommerce.models.ObjectError;
import com.example.icommerce.models.Response;
import com.example.icommerce.models.UserRequestModel;

public class OrderControllerTest extends BaseControllerTest {

    @Override
    protected String getEndPoint () {
        return "/orders";
    }

    private final String ADD_ENDPOINT      = "/add";
    private final String REMOVE_ENDPOINT   = "/remove";
    private final String CHECKOUT_ENDPOINT = "/checkout";

    @Test
    public void testAddProductToOrder () {
        String                        productSku  = "ICMAIP000009";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("productSku", productSku);

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(getUrl() + ADD_ENDPOINT)
            .queryParams(queryParams)
            .build();

        HttpEntity httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, httpEntity, Response.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());

        Map response = (Map) responseEntity.getBody().getData();
        Assertions.assertNotEquals("0", response.get("total").toString());
        Assertions.assertNotEquals("{}", response.get("order").toString());

        List orders = (List) response.get("order");
        Assertions.assertEquals(1, orders.size());

    }

    @Test
    public void testRemoveProductToOrder () {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("productSku", "123456789987");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(getUrl() + REMOVE_ENDPOINT)
            .queryParams(queryParams)
            .build();

        HttpEntity httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, httpEntity, Response.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
    }

    @Test
    public void testGetOrders () {
        HttpEntity httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.exchange(getUrl(), HttpMethod.GET, httpEntity, Response.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());

        Map response = (Map) responseEntity.getBody().getData();
        Assertions.assertEquals("0", response.get("total").toString());
        Assertions.assertEquals("[]", response.get("order").toString());
    }

    @Test
    public void TestCheckout () {
        HttpEntity httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.exchange(getUrl(), HttpMethod.GET, httpEntity, Response.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());

        Map response = (Map) responseEntity.getBody().getData();
        Assertions.assertEquals("0", response.get("total").toString());
        Assertions.assertNotEquals("", response.get("order").toString());

    }
}
