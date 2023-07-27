package com.luisguilherme.dscatalog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luisguilherme.dscatalog.dto.EmailDTO;
import com.luisguilherme.dscatalog.dto.NewPasswordDTO;
import com.luisguilherme.dscatalog.services.AuthService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthService service;
	
	@PostMapping(value = "/recover-token")
	public ResponseEntity<Void> createRecoverToken(@RequestBody @Valid EmailDTO dto){	
		service.createRecoverToken(dto);
		return ResponseEntity.noContent().build();			
	}
	
	@PutMapping(value = "/new-password")
	public ResponseEntity<Void> saveNewPassword(@RequestBody @Valid NewPasswordDTO dto){	
		service.saveNewPassword(dto);
		return ResponseEntity.noContent().build();			
	}
	

}
