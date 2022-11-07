package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	List<Role> findByName(String role);
}
