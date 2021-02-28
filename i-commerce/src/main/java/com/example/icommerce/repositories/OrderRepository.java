package com.example.icommerce.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.icommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getByOrderNumber(String orderNumber);

    boolean existsByOrderNumber (String orderNumber);

    Page<Order> findAllByUser_Username (String user_username, Pageable pageable);
}
