package com.example.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springjwt.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {//CrudRepository

    Role findByName(String name);

}