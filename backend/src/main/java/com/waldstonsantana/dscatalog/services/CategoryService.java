package com.waldstonsantana.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waldstonsantana.dscatalog.dtos.CategoryDto;
import com.waldstonsantana.dscatalog.entities.Category;
import com.waldstonsantana.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryDto> findAll() {
		List<Category> list = categoryRepository.findAll();
		
		return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
		
		
	}
}
