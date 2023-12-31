package com.luisguilherme.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.luisguilherme.dscatalog.dto.CategoryDTO;
import com.luisguilherme.dscatalog.entities.Category;
import com.luisguilherme.dscatalog.repositories.CategoryRepository;
import com.luisguilherme.dscatalog.services.exceptions.DatabaseException;
import com.luisguilherme.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;


@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAll(Pageable pageable) {		
		Page<Category> result = repository.findAll(pageable);
		return result.map(x -> new CategoryDTO(x));
	}	
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(@PathVariable Long id) {		
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not Found"));
		return new CategoryDTO(entity);		
	}	
	
	@Transactional
	public CategoryDTO insert(@Valid @RequestBody CategoryDTO dto) {	
		Category entity = new Category();		
		entity.setName(dto.getName());	
		entity = repository.save(entity);		
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(@Valid @PathVariable @RequestBody Long id, CategoryDTO dto) {	
		try {
			Category entity = repository.getReferenceById(id);		
			entity.setName(dto.getName());	
			entity = repository.save(entity);		
			return new CategoryDTO(entity);
		}
		catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {			
			repository.deleteById(id);	
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
		
	}
	
	
}

