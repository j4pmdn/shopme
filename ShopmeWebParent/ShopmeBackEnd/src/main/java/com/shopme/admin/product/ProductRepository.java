package com.shopme.admin.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shopme.common.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	public Long countById(Integer id);

}

