package com.waldstonsantana.dscatalog.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.waldstonsantana.dscatalog.dtos.ProductDto;
import com.waldstonsantana.dscatalog.services.ProductService;

@RestController
@RequestMapping(value = "/produtos")
public class ProductController {

	@Autowired
	private ProductService categoryService;

	@GetMapping
	public ResponseEntity<Page<ProductDto>> findAll(Pageable pageable) {
		

		Page<ProductDto> list = categoryService.findAllPaged(pageable);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDto> findById(@PathVariable Long id) {

		ProductDto dto = categoryService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<ProductDto> insert(@RequestBody ProductDto dto) {
		dto = categoryService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto dto) {
		dto = categoryService.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		categoryService.delete(id);

		return ResponseEntity.noContent().build();
	}

}
