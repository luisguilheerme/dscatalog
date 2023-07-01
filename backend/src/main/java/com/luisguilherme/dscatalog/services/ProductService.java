package com.luisguilherme.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luisguilherme.dscatalog.dto.CategoryDTO;
import com.luisguilherme.dscatalog.dto.ProductDTO;
import com.luisguilherme.dscatalog.entities.Category;
import com.luisguilherme.dscatalog.entities.Product;
import com.luisguilherme.dscatalog.repositories.CategoryRepository;
import com.luisguilherme.dscatalog.repositories.ProductRepository;
import com.luisguilherme.dscatalog.services.exceptions.DatabaseException;
import com.luisguilherme.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {		
		Page<Product> result = repository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));
	}	
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {		
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not Found"));
		return new ProductDTO(entity, entity.getCategories());		
	}	
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {	
		Product entity = new Product();	
		copyDtoToEntity(dto, entity);	
		entity = repository.save(entity);		
		return new ProductDTO(entity);
	}	


	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {	
		try {
			Product entity = repository.getOne(id);		
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);		
			return new ProductDTO(entity);
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
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		
		entity.getCategories().clear();
		for(CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}
		
	}
	
	
}

