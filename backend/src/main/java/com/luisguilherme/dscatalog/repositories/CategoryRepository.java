package com.luisguilherme.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luisguilherme.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
