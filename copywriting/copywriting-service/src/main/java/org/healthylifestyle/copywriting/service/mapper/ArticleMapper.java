package org.healthylifestyle.copywriting.service.mapper;

import java.util.List;

import org.healthylifestyle.copywriting.common.dto.ArticleDto;
import org.healthylifestyle.copywriting.model.Article;

public interface ArticleMapper {
	ArticleDto articleToDto(Article article);
	
	List<ArticleDto> articlesToDtos(List<Article> articles);
}
