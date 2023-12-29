package org.healthylifestyle.copywriting.service.mapper;

import java.util.List;

import org.healthylifestyle.copywriting.common.dto.CategoryDto;
import org.healthylifestyle.copywriting.model.Category;

public interface CategoryMapper {

	CategoryDto categoryToDto(Category category);

	List<CategoryDto> categoriesToDtos(List<Category> categories);
}
