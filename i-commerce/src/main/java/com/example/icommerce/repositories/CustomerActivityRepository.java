package com.example.icommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.icommerce.entities.CustomerActivity;

public interface CustomerActivityRepository extends JpaRepository<CustomerActivity, Long> {

    List<CustomerActivity> findAllByUser_Username(String username);
}
