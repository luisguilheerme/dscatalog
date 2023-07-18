package com.luisguilherme.dscatalog.dto;

import java.util.HashSet;
import java.util.Set;

import com.luisguilherme.dscatalog.entities.User;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class UserDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 2, max = 30, message = "Deve ter entre 2 e 30 caracteres")
	@NotBlank(message = "Campo obrigatório")
	private String firstName;
	
	@Size(min = 2, max = 30, message = "Deve ter entre 2 e 30 caracteres")
	@NotBlank(message = "Campo obrigatório")
	private String lastName;
	@Email(message = "Email inválido")
	@NotBlank(message = "Campo obrigatório")
	private String email;
	
	Set<RoleDTO> roles = new HashSet();
	
	public UserDTO() {
		
	}

	public UserDTO(Long id, String firstName, String lastName, String email, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}

	
}
