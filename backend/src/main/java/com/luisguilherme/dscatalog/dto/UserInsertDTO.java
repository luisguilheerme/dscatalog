package com.luisguilherme.dscatalog.dto;

import com.luisguilherme.dscatalog.services.validation.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

	@Size(min = 8, message = "Deve ter no mínimo 8 caracteres")
	@NotBlank(message = "Campo obrigatório")
	private String password;
	
	UserInsertDTO() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
