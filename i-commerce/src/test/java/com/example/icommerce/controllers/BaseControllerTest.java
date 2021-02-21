package com.example.icommerce.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import com.example.icommerce.ICommerceApplication;
import com.example.icommerce.constants.Constants;
import com.example.icommerce.utilities.SkuGenerator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = ICommerceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseControllerTest {

    @LocalServerPort
    protected int port;

    protected TestRestTemplate restTemplate;

    @BeforeEach
    public void init () {
        SkuGenerator.reset();
        restTemplate = new TestRestTemplate();
    }

    protected String getUrl () {
        return "http://localhost:" + port + "/api" + getEndPoint();
    }

    protected abstract String getEndPoint ();

    protected HttpHeaders getDefaultHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(Constants.REQUEST_IDENTITY_ID_HEADER, "user");

        return httpHeaders;
    }
}
