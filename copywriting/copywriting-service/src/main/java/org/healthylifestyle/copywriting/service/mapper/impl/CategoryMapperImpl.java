package org.healthylifestyle.copywriting.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.copywriting.common.dto.CategoryDto;
import org.healthylifestyle.copywriting.model.Category;
import org.healthylifestyle.copywriting.service.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {

	@Override
	public CategoryDto categoryToDto(Category category) {
		CategoryDto dto = new CategoryDto();
		dto.setId(category.getId());
		dto.setTitle(category.getOriginalTitle());

		return dto;
	}

	@Override
	public List<CategoryDto> categoriesToDtos(List<Category> categories) {
		List<CategoryDto> dtos = new ArrayList<>();
		categories.forEach(c -> dtos.add(categoryToDto(c)));

		return dtos;
	}

}
