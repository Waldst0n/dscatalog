package com.waldstonsantana.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waldstonsantana.dscatalog.dtos.CategoryDto;
import com.waldstonsantana.dscatalog.entities.Category;
import com.waldstonsantana.dscatalog.repositories.CategoryRepository;
import com.waldstonsantana.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public List<CategoryDto> findAll() {
		List<Category> list = categoryRepository.findAll();

		return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		Optional<Category> obj = categoryRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada!"));
		return new CategoryDto(entity);

	}

	@Transactional
	public CategoryDto insert(CategoryDto dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = categoryRepository.save(entity);

		return new CategoryDto(entity);

	}

	@Transactional
	public CategoryDto update(Long id, CategoryDto dto) {
		
		try {
		Category entity = categoryRepository.getReferenceById(id);
		entity.setName(dto.getName());
		
		entity = categoryRepository.save(entity);
		
		return new CategoryDto(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}

}
