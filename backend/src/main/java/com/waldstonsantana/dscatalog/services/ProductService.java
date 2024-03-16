package com.waldstonsantana.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.waldstonsantana.dscatalog.dtos.ProductDto;
import com.waldstonsantana.dscatalog.entities.Product;
import com.waldstonsantana.dscatalog.repositories.ProductRepository;
import com.waldstonsantana.dscatalog.services.exceptions.DatabaseException;
import com.waldstonsantana.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

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
		//entity.setName(dto.getName());
		entity = productRepository.save(entity);

		return new ProductDto(entity);

	}

	@Transactional
	public ProductDto update(Long id, ProductDto dto) {

		try {
			Product entity = productRepository.getReferenceById(id);
			//entity.setName(dto.getName());

			entity = productRepository.save(entity);

			return new ProductDto(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado " + id);
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
