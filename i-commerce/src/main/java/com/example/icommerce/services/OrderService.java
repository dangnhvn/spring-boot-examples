package com.example.icommerce.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.ProductPrice;
import com.example.icommerce.models.ProductDTO;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService {

    @Autowired
    private ProductService productService;

    private Map<String, Integer> cart = new HashMap<>();

    public BigDecimal getTotalPrice () {

        return cart.entrySet().stream()
            .map(entry -> {
                ProductPrice productPrice = productService.getLatestPrice(entry.getKey());
                double price = Objects.isNull(productPrice) ? 0.0 : productPrice.getPrice();
                return BigDecimal.valueOf(price * entry.getValue());
            })
            .reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO);
    }

    public List<Map<String, Object>> getProductsInOrder () {
        List<Map<String, Object>> orderList = new ArrayList<>();

        cart.forEach((sku, quantity) -> {
            Map<String, Object> cartMap = new HashMap<>();
            ProductDTO product = productService.getProductDTOBySku(sku);
            cartMap.put("product", product);
            cartMap.put("quantity", quantity);

            orderList.add(cartMap);
        });

        return Collections.unmodifiableList(orderList);
    }

    public Map<String, Integer> getProductSkusInOrder () {
        return Collections.unmodifiableMap(cart);
    }

    public void checkout() {
        cart.clear();
    }

    public void clearOrder() {
        cart.clear();
    }

    public void addProduct (String productSku) {
        Product product = productService.getProductBySku(productSku);
        if(product != null) {

            if ( cart.containsKey(productSku) ) {
                cart.replace(productSku, cart.get(productSku) + 1);
            }
            else {
                cart.put(productSku, 1);
            }
        }
    }

    public void removeProduct(String productSku) {
        Product product = productService.getProductBySku(productSku);
        if(product != null) {
            if ( cart.containsKey(productSku) ) {
                if ( cart.get(productSku) > 1 ) {
                    cart.replace(productSku, cart.get(productSku) - 1);
                }
                else if ( cart.get(productSku) == 1 ) {
                    cart.remove(productSku);
                }
            }
        }
    }
}
