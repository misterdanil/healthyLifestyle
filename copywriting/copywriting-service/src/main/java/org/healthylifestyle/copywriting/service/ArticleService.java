package org.healthylifestyle.copywriting.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.copywriting.common.dto.SaveArticleRequest;
import org.healthylifestyle.copywriting.model.Article;

public interface ArticleService {
	Article findById(Long id);

	Article save(SaveArticleRequest saveRequest) throws ValidationException;

	List<Article> findAll(int page);

	List<Article> finAllByTitle(String title, int page);

	long count();

	List<Article> findAllByUser(Long userId, int page);

	List<Article> findAllByCategory(Long id, int page);
}
