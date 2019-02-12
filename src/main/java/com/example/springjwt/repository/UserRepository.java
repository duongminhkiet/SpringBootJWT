package com.example.springjwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springjwt.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String email);
    List<User> findAll();
    User findById(int id);
}