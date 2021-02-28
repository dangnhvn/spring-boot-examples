package com.example.icommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.icommerce.entities.UserActivity;

public interface CustomerActivityRepository extends JpaRepository<UserActivity, Long> {

    List<UserActivity> findAllByUser_Username(String username);
}
