package com.example.icommerce.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.icommerce.constants.Constants;
import com.example.icommerce.constants.ErrorCode;
import com.example.icommerce.models.ObjectError;
import com.example.icommerce.models.Response;
import com.example.icommerce.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public Response<?> getOrders () {
        return getProductsInOrder();
    }

    @GetMapping("/checkout")
    public Response<?> checkout (@Valid @NotEmpty @RequestHeader(Constants.REQUEST_IDENTITY_ID_HEADER) String identityId) {
        String orderNumber = cartService.checkout(identityId);
        if ( orderNumber == null ) {
            return new Response<>(new ObjectError(ErrorCode.CREATION_ERROR, "Order creation is fail due to cart is empty"));
        }
        return new Response<>(cartService.getOrderDetail(orderNumber));
    }

    @PutMapping("/add")
    public Response<?> addProductToOrder (@Valid @NotEmpty @RequestParam("productSku") String productSku) {
        if ( StringHelper.isEmpty(productSku) ) {
            throw new IllegalArgumentException("'productSku' parameter must contain value");
        }

        cartService.addProduct(productSku);

        return getProductsInOrder();
    }

    @PutMapping("/remove")
    public Response<?> removeProductToOrder (@Valid @NotEmpty @RequestParam("productSku") String productSku) {
        if ( StringHelper.isEmpty(productSku) ) {
            throw new IllegalArgumentException("'productSku' parameter must contain value");
        }

        cartService.removeProduct(productSku);

        return getProductsInOrder();
    }

    private Response<?> getProductsInOrder () {
        Map<String, Object> map = new HashMap<>();
        map.put("order", cartService.getOrdersInCart());
        map.put("total", cartService.getTotalPrice().toString());

        return new Response<>(map);
    }
}
