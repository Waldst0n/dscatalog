package com.waldstonsantana.dscatalog.services;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.waldstonsantana.dscatalog.dtos.CategoryDto;
import com.waldstonsantana.dscatalog.dtos.ProductDto;
import com.waldstonsantana.dscatalog.entities.Category;
import com.waldstonsantana.dscatalog.entities.Product;
import com.waldstonsantana.dscatalog.repositories.CategoryRepository;
import com.waldstonsantana.dscatalog.repositories.ProductRepository;
import com.waldstonsantana.dscatalog.services.exceptions.DatabaseException;
import com.waldstonsantana.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.AssertFalse.List;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDto> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = productRepository.findAll(pageRequest);

		return list.map(x -> new ProductDto(x, x.getCategories()));
	}

	@Transactional(readOnly = true)
	public ProductDto findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada!"));
		return new ProductDto(entity, entity.getCategories());

	}

	@Transactional
	public ProductDto insert(ProductDto dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		
		
		
		entity = productRepository.save(entity);

		return new ProductDto(entity);

	}

	@Transactional
	public ProductDto update(Long id, ProductDto dto) {

		try {
			Product entity = productRepository.getReferenceById(id);
			copyDtoToEntity(dto, entity);


			entity = productRepository.save(entity);

			return new ProductDto(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
		}
	}

	private void copyDtoToEntity(ProductDto dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		
		entity.getCategories().clear();
		for(CategoryDto catDTO: dto.getCategories()) {
			Category category = categoryRepository.getReferenceById(catDTO.getId());
			entity.getCategories().add(category);
		}
		
		
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {

		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {

			productRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

}
