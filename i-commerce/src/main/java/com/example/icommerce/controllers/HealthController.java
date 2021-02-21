package com.example.icommerce.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.icommerce.models.Response;

@RestController
@RequestMapping(value = "/api/health")
public class HealthController {

    @GetMapping
    public Response<?> getHealth() {
        Map<String, String> healthMap = new HashMap<>();
        healthMap.put("status", "UP");

        return new Response<>(healthMap);
    }
}
