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
import com.example.icommerce.models.ObjectPager;
import com.example.icommerce.models.PagingResponse;
import com.example.icommerce.models.ProductRequestModel;
import com.example.icommerce.models.Response;

public class ProductControllerTest extends BaseControllerTest {

    @Override
    protected String getEndPoint () {
        return "/products";
    }

    @Test
    public void testCreateProduct () {
        ProductRequestModel model = new ProductRequestModel();
        model.setName("product name");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(101028.0);

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(model, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.postForEntity(getUrl(), httpEntity, Response.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());

        Map response = (Map) responseEntity.getBody().getData();
        String sku = response.get("sku").toString();
        Assertions.assertTrue(response.get("sku").toString().startsWith("ICMPRO"));
        Assertions.assertEquals("product name", response.get("name").toString());
        Assertions.assertEquals("product brand", response.get("brand").toString());
        Assertions.assertEquals("product color", response.get("color").toString());
        Assertions.assertEquals("product description", response.get("description").toString());
        Assertions.assertEquals(Double.valueOf(101028.0), response.get("price"));

        responseEntity = restTemplate.exchange(getUrl() + "?productSku=" + sku, HttpMethod.DELETE, httpEntity, Response.class);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());

        response = (Map) responseEntity.getBody().getData();
        Assertions.assertEquals("SUCCEED", response.get("status").toString());

    }

    @Test
    public void testUpdateProduct () {
        ProductRequestModel model = new ProductRequestModel();
        model.setProductSku("ICMAIP000009");
        model.setName("product name");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(10122028.0);

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(model, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.exchange(getUrl(), HttpMethod.PUT, httpEntity, Response.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());

        Map response = (Map) responseEntity.getBody().getData();
        Assertions.assertEquals("ICMAIP000009", response.get("sku").toString());
        Assertions.assertEquals("product name", response.get("name").toString());
        Assertions.assertEquals("product brand", response.get("brand").toString());
        Assertions.assertEquals("product color", response.get("color").toString());
        Assertions.assertEquals("product description", response.get("description").toString());
        Assertions.assertEquals(Double.valueOf(10122028.0), response.get("price"));
    }

    @Test
    public void testUpdateProductWithError () {
        String sku = "123456789987";
        ProductRequestModel model = new ProductRequestModel();
        model.setProductSku(sku);
        model.setName("product name");
        model.setBrand("product brand");
        model.setColor("product color");
        model.setDescription("product description");
        model.setPrice(10122028.0);

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(model, getDefaultHeader());
        ResponseEntity<Response> responseEntity = restTemplate.exchange(getUrl(), HttpMethod.PUT, httpEntity, Response.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getError());

        ObjectError error = responseEntity.getBody().getError();
        Assertions.assertEquals("Product with SKU: '" + sku + "' doesnot exist in Database", error.getMessage());
        Assertions.assertEquals(ErrorCode.INVALID_REQUEST, error.getCode());
    }

    @Test
    public void testGetProducts () {
        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<PagingResponse> responseEntity = restTemplate.exchange(getUrl(), HttpMethod.GET, httpEntity, PagingResponse.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Assertions.assertNotNull(responseEntity.getBody().getPaging());

        ObjectPager pager = responseEntity.getBody().getPaging();
        Assertions.assertEquals(0, pager.getPage());
        Assertions.assertEquals(20, pager.getLimit());
        Assertions.assertEquals(5, pager.getTotalPage());

        List response = (List) responseEntity.getBody().getData();
        Assertions.assertEquals(20, response.size());
    }

    @Test
    public void testGetProductsWithLimitation () {
        MultiValueMap<String, String> queryParams   = new LinkedMultiValueMap<>();
        queryParams.add("limit", "10");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(getUrl())
            .queryParams(queryParams)
            .build();

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<PagingResponse> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, PagingResponse.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Assertions.assertNotNull(responseEntity.getBody().getPaging());

        ObjectPager pager = responseEntity.getBody().getPaging();
        Assertions.assertEquals(0, pager.getPage());
        Assertions.assertEquals(10, pager.getLimit());
        Assertions.assertEquals(10, pager.getTotalPage());

        List response = (List) responseEntity.getBody().getData();
        Assertions.assertEquals(10, response.size());
    }

    @Test
    public void testGetProductsWithLimitationAndPagination () {
        MultiValueMap<String, String> queryParams   = new LinkedMultiValueMap<>();
        queryParams.add("limit", "5");
        queryParams.add("page", "2");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(getUrl())
            .queryParams(queryParams)
            .build();

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<PagingResponse> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, PagingResponse.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Assertions.assertNotNull(responseEntity.getBody().getPaging());

        ObjectPager pager = responseEntity.getBody().getPaging();
        Assertions.assertEquals(2, pager.getPage());
        Assertions.assertEquals(5, pager.getLimit());
        Assertions.assertEquals(20, pager.getTotalPage());

        List response = (List) responseEntity.getBody().getData();
        Assertions.assertEquals(5, response.size());
    }

    @Test
    public void testGetProductsWithSearchName () {
        MultiValueMap<String, String> queryParams   = new LinkedMultiValueMap<>();
        queryParams.add("name", "Iphone");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(getUrl())
            .queryParams(queryParams)
            .build();

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<PagingResponse> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, PagingResponse.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Assertions.assertNotNull(responseEntity.getBody().getPaging());

        ObjectPager pager = responseEntity.getBody().getPaging();
        Assertions.assertEquals(0, pager.getPage());
        Assertions.assertEquals(20, pager.getLimit());
        Assertions.assertEquals(1, pager.getTotalPage());

        List response = (List) responseEntity.getBody().getData();
        Assertions.assertEquals(20, response.size());
    }

    @Test
    public void testGetProductsWithSortByNameAsc () {
        MultiValueMap<String, String> queryParams   = new LinkedMultiValueMap<>();
        queryParams.add("sortBy", "name");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(getUrl())
            .queryParams(queryParams)
            .build();

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<PagingResponse> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, PagingResponse.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Assertions.assertNotNull(responseEntity.getBody().getPaging());

        ObjectPager pager = responseEntity.getBody().getPaging();
        Assertions.assertEquals(0, pager.getPage());
        Assertions.assertEquals(20, pager.getLimit());
        Assertions.assertEquals(5, pager.getTotalPage());

        List response = (List) responseEntity.getBody().getData();
        Assertions.assertEquals(20, response.size());
    }

    @Test
    public void testGetProductsWithSortByNameDesc () {
        MultiValueMap<String, String> queryParams   = new LinkedMultiValueMap<>();
        queryParams.add("sortBy", "-name");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString(getUrl())
            .queryParams(queryParams)
            .build();

        HttpEntity<ProductRequestModel> httpEntity = new HttpEntity<>(null, getDefaultHeader());
        ResponseEntity<PagingResponse> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, httpEntity, PagingResponse.class);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertNotNull(responseEntity.getBody().getData());
        Assertions.assertNotNull(responseEntity.getBody().getPaging());

        ObjectPager pager = responseEntity.getBody().getPaging();
        Assertions.assertEquals(0, pager.getPage());
        Assertions.assertEquals(20, pager.getLimit());
        Assertions.assertEquals(5, pager.getTotalPage());

        List response = (List) responseEntity.getBody().getData();
        Assertions.assertEquals(20, response.size());
    }
}
