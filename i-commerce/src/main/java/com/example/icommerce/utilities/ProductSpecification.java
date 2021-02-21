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

package com.example.icommerce.utilities;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;

import org.hibernate.internal.util.StringHelper;
import org.springframework.data.jpa.domain.Specification;

import com.example.icommerce.entities.Product;
import com.example.icommerce.entities.ProductPrice;
import com.example.icommerce.metamodels.Product_;

public final class ProductSpecification {

    private ProductSpecification(){}

    public static Specification<Product> skuEqual (String sku) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.equal(root.get(Product_.SKU), sku);
            return predicate;
        };
    }

    public static Specification<Product> nameLike (String name) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get(Product_.NAME), StringHelper.isEmpty(name) ? null : "%" + name + "%");
            return predicate;
        };
    }

    public static Specification<Product> brandLike (String brand) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get(Product_.BRAND), StringHelper.isEmpty(brand) ? null : "%" + brand + "%");
            return predicate;
        };
    }

    public static Specification<Product> colorLike (String color) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.like(root.get(Product_.COLOR), StringHelper.isEmpty(color) ? null : "%" + color + "%");
            return predicate;
        };
    }

    public static Specification<Product> priceGreaterThan (Double price) {
        return (root, query, criteriaBuilder) -> {
            ListJoin<Product, ProductPrice> productJoin = root.joinList(Product_.PRICES);
            Predicate                       predicate   = criteriaBuilder.greaterThan(productJoin.get("price"), price);
            return predicate;
        };
    }

}
