package com.example.icommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.ProductPrice;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {

    List<ProductPrice> findAllByProduct_Sku (String productSku);

    List<ProductPrice> findAllByProduct (Product product);
}
