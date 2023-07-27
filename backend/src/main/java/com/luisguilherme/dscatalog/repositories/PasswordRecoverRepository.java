package com.luisguilherme.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisguilherme.dscatalog.entities.PasswordRecover;


public interface PasswordRecoverRepository  extends JpaRepository<PasswordRecover, Long>{

}
