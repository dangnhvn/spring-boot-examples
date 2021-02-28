package com.example.icommerce.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.icommerce.constants.ErrorCode;
import com.example.icommerce.dtos.ProductDTO;
import com.example.icommerce.exceptions.ICommerceException;
import com.example.icommerce.models.ObjectError;
import com.example.icommerce.models.PagingResponse;
import com.example.icommerce.models.ProductPagingRequest;
import com.example.icommerce.models.ProductRequestModel;
import com.example.icommerce.models.Response;
import com.example.icommerce.services.ProductService;

@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public PagingResponse<?> getProducts (
        @Valid @ModelAttribute ProductPagingRequest pagingRequest) {
        Page<ProductDTO> page = productService.getProducts(pagingRequest);

        return new PagingResponse<>(page, pagingRequest);

    }

    @PostMapping
    public Response<ProductDTO> createProduct (@Valid @RequestBody ProductRequestModel request) {
        ProductDTO product = productService.createProduct(request);

        if ( product != null ) {
            return new Response<>(product);
        }

        return new Response<>(new ObjectError(ErrorCode.SERVER_ERROR, "SERVER ERROR"));

    }

    @PutMapping
    public Response<ProductDTO> updateProduct (@Valid @RequestBody ProductRequestModel request) throws ICommerceException {
        boolean success = productService.updateProduct(request);

        if ( success ) {
            ProductDTO product = productService.getProductDTOBySku(request.getProductSku());
            return new Response<>(product);
        }

        return new Response<>(new ObjectError(ErrorCode.SERVER_ERROR, "SERVER ERROR"));

    }

    @DeleteMapping
    public Response<?> deleteProduct(
        @Valid @NotEmpty(message = "'product_sku' parameter is empty") @RequestParam("productSku") String productSku) {
        boolean success = productService.deleteProductBySku(productSku);

        if(success) {
            Map<String, String> status = new HashMap<>();
            status.put("status", "SUCCEED");

            return new Response<>(status);
        }

        return new Response<>(new ObjectError(ErrorCode.SERVER_ERROR, "SERVER ERROR"));
    }


}
