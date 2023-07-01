package com.luisguilherme.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luisguilherme.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
