package com.waldstonsantana.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waldstonsantana.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
