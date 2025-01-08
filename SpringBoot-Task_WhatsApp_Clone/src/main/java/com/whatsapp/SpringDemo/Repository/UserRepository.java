package com.whatsapp.SpringDemo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatsapp.SpringDemo.Entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method to find a user by email
    Optional<User> findByEmail(String email);

    // Check if a user already exists by email
    boolean existsByEmail(String email);
    
    User findByUserId(Long userId);
}
