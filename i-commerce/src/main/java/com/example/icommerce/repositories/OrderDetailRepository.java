package com.example.icommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.icommerce.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
