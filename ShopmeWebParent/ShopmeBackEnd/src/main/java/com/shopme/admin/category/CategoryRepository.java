package com.shopme.admin.category;

import org.springframework.data.repository.CrudRepository;

import com.shopme.common.entity.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
