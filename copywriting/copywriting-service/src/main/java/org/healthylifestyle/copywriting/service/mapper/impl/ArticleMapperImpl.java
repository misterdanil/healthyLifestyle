package org.healthylifestyle.copywriting.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.copywriting.common.dto.ArticleDto;
import org.healthylifestyle.copywriting.model.Article;
import org.healthylifestyle.copywriting.service.mapper.ArticleMapper;
import org.healthylifestyle.filesystem.service.mapper.ImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapperImpl implements ArticleMapper {
	@Autowired
	private ImageMapper imageMapper;

	@Override
	public ArticleDto articleToDto(Article article) {
		ArticleDto dto = new ArticleDto();
		dto.setId(article.getId());
		dto.setTitle(article.getOriginalTitle());
		dto.setImage(imageMapper.imageToDto(article.getImage()));

		return dto;
	}

	@Override
	public List<ArticleDto> articlesToDtos(List<Article> articles) {
		List<ArticleDto> dtos = new ArrayList<>();

		articles.forEach(a -> dtos.add(articleToDto(a)));

		return dtos;

	}

}
