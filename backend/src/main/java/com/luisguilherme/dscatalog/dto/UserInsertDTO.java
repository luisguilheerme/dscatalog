package com.luisguilherme.dscatalog.dto;

import com.luisguilherme.dscatalog.entities.User;

public class UserInsertDTO extends UserDTO {

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
