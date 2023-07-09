package com.luisguilherme.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.luisguilherme.dscatalog.dto.RoleDTO;
import com.luisguilherme.dscatalog.dto.UserDTO;
import com.luisguilherme.dscatalog.dto.UserInsertDTO;
import com.luisguilherme.dscatalog.entities.Role;
import com.luisguilherme.dscatalog.entities.User;
import com.luisguilherme.dscatalog.repositories.RoleRepository;
import com.luisguilherme.dscatalog.repositories.UserRepository;
import com.luisguilherme.dscatalog.services.exceptions.DatabaseException;
import com.luisguilherme.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAll(Pageable pageable) {		
		Page<User> result = repository.findAll(pageable);
		return result.map(x -> new UserDTO(x));
	}	
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {		
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not Found"));
		return new UserDTO(entity);		
	}	
	
	@Transactional
	public UserDTO insert(UserInsertDTO dto) {	
		User entity = new User();	
		copyDtoToEntity(dto, entity);	
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);		
		return new UserDTO(entity);
	}	


	@Transactional
	public UserDTO update(Long id, UserDTO dto) {	
		try {
			User entity = repository.getOne(id);		
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);		
			return new UserDTO(entity);
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
	
	private void copyDtoToEntity(UserDTO dto, User entity) {
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());	
		entity.getRoles().clear();
		for(RoleDTO roleDTO : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDTO.getId());
			entity.getRoles().add(role);
		}
		
	}
	
}

