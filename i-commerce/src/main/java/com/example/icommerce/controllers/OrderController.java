////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * Copyright © 2021 Unified Social, Inc.
 * 180 Madison Avenue, 23rd Floor, New York, NY 10016, U.S.A.
 * All rights reserved.
 *
 * This software (the "Software") is provided pursuant to the license agreement you entered into with Unified Social,
 * Inc. (the "License Agreement").  The Software is the confidential and proprietary information of Unified Social,
 * Inc., and you shall use it only in accordance with the terms and conditions of the License Agreement.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND "AS AVAILABLE."  UNIFIED SOCIAL, INC. MAKES NO WARRANTIES OF ANY KIND, WHETHER
 * EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 */

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package com.example.icommerce.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.icommerce.constants.Constants;
import com.example.icommerce.models.Response;
import com.example.icommerce.services.OrderService;
import com.example.icommerce.services.UserService;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Response<?> getOrders () {
        return getProductsInOrder();
    }

    @GetMapping("/checkout")
    public Response<?> checkout (@Valid @NotEmpty @RequestHeader(Constants.REQUEST_IDENTITY_ID_HEADER) String identityId) {
        if(userService.getUserByUsername(identityId) == null) {
            throw new IllegalArgumentException("User '" + identityId + "' not found");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("order", orderService.getProductsInOrder());
        map.put("total", orderService.getTotalPrice());

        orderService.checkout();

        return new Response<>(map);
    }

    @PutMapping("/add")
    public Response<?> addProductToOrder (@Valid @NotEmpty @RequestParam("productSku") String productSku) {
        if ( StringHelper.isEmpty(productSku) ) {
            throw new IllegalArgumentException("'productSku' parameter must contain value");
        }

        orderService.addProduct(productSku);

        return getProductsInOrder();
    }

    @PutMapping("/remove")
    public Response<?> removeProductToOrder (@Valid @NotEmpty @RequestParam("productSku") String productSku) {
        if ( StringHelper.isEmpty(productSku) ) {
            throw new IllegalArgumentException("'productSku' parameter must contain value");
        }

        orderService.removeProduct(productSku);

        return getProductsInOrder();
    }

    private Response<?> getProductsInOrder () {
        Map<String, Object> map = new HashMap<>();
        map.put("order", orderService.getProductsInOrder());
        map.put("total", orderService.getTotalPrice().toString());

        return new Response<>(map);
    }

}
