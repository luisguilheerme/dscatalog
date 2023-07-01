package com.luisguilherme.dscatalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luisguilherme.dscatalog.dto.CategoryDTO;
import com.luisguilherme.dscatalog.entities.Category;
import com.luisguilherme.dscatalog.repositories.CategoryRepository;
import com.luisguilherme.dscatalog.services.exceptions.ResourceNotFoundException;



@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {		
		List<Category> result = repository.findAll();
		return result.stream().map(x -> new CategoryDTO(x)).toList();
	}	
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {		
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not Found"));
		return new CategoryDTO(entity);		
	}	
	
	
}

