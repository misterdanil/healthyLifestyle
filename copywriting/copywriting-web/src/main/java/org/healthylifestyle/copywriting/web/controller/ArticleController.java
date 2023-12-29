package org.healthylifestyle.copywriting.web.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ResponseEntityResolver;
import org.healthylifestyle.copywriting.common.dto.ArticleDto;
import org.healthylifestyle.copywriting.common.dto.SaveArticleRequest;
import org.healthylifestyle.copywriting.model.Article;
import org.healthylifestyle.copywriting.service.ArticleService;
import org.healthylifestyle.copywriting.service.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleMapper mapper;

	@PostMapping("/article")
	public ResponseEntity<Object> save(@RequestBody SaveArticleRequest saveRequest) {
		Article article;
		try {
			article = articleService.save(saveRequest);
		} catch (ValidationException e) {
			return ResponseEntityResolver.getBuilder(e.getType()).body(e.getErrorResult());
		}

		try {
			return ResponseEntity.created(new URI("http://localhost:8080/article/" + article.getId())).build();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Exception occurred while saving article. Couldn't form article uri", e);
		}
	}

	@GetMapping("/user/{userId}/articles")
	public ResponseEntity<Object> findAllByUser(@PathVariable Long userId, @RequestParam int page) {
		List<Article> articles = articleService.findAllByUser(userId, page);

		List<ArticleDto> dtos = mapper.articlesToDtos(articles);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/category/{id}/articles")
	public ResponseEntity<List<ArticleDto>> findAllByCategory(@PathVariable Long id, @RequestParam int page) {
		List<Article> articles = articleService.findAllByCategory(id, page);

		List<ArticleDto> dtos = mapper.articlesToDtos(articles);

		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/articles")
	public ResponseEntity<List<ArticleDto>> findAll(@RequestParam int page) {
		List<Article> articles = articleService.findAll(page);

		List<ArticleDto> dtos = mapper.articlesToDtos(articles);

		return ResponseEntity.ok(dtos);
	}
}
