package com.luisguilherme.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisguilherme.dscatalog.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByAuthority(String authority);
}
