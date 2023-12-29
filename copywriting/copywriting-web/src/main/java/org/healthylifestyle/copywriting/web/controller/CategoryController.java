package org.healthylifestyle.copywriting.web.controller;

import java.util.List;

import org.healthylifestyle.copywriting.common.dto.CategoryDto;
import org.healthylifestyle.copywriting.model.Category;
import org.healthylifestyle.copywriting.service.CategoryService;
import org.healthylifestyle.copywriting.service.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryMapper mapper;
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> findAll() {
		List<Category> categories = categoryService.findAll();
		List<CategoryDto> dtos = mapper.categoriesToDtos(categories);

		return ResponseEntity.ok(dtos);
	}
}
