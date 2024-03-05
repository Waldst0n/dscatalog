package com.waldstonsantana.dscatalog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waldstonsantana.dscatalog.dtos.CategoryDto;
import com.waldstonsantana.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> findAll() {
		
		List<CategoryDto> list = categoryService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
		
		CategoryDto dto = categoryService.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	
}
