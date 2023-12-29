package org.healthylifestyle.copywriting.service;

import java.util.List;

import org.healthylifestyle.copywriting.model.Category;

public interface CategoryService {
	Category save(Category category);
	
	Category findById(Long id);
	
	List<Category> findAll();
}
