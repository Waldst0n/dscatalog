package com.waldstonsantana.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waldstonsantana.dscatalog.entities.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

	
}
