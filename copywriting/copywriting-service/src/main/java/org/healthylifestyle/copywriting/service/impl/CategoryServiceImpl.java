package org.healthylifestyle.copywriting.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.copywriting.model.Category;
import org.healthylifestyle.copywriting.repository.CategoryRepository;
import org.healthylifestyle.copywriting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Category findById(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories = new ArrayList<>();
		categoryRepository.findAll().forEach(category -> categories.add(category));

		return categories;
	}

}
