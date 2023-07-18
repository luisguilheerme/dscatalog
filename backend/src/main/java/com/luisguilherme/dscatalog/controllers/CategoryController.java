package com.luisguilherme.dscatalog.controllers;

import java.net.URI;
import java.util.List;

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

import com.luisguilherme.dscatalog.dto.CategoryDTO;
import com.luisguilherme.dscatalog.services.CategoryService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity<Page<CategoryDTO>> findAll(Pageable pageable){		
		Page<CategoryDTO> dto = service.findAll(pageable);
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){		
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto){		
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
			
	}
	
	@PutMapping(value = "/{id}")	
	public ResponseEntity<CategoryDTO> update(@Valid @PathVariable Long id, @RequestBody CategoryDTO dto){		
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{id}")	
	public ResponseEntity<Void> delete(@PathVariable Long id){		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
