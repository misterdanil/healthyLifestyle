package org.healthylifestyle.copywriting.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.common.error.BindingResultFactory;
import org.healthylifestyle.common.error.Type;
import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.common.web.ErrorParser;
import org.healthylifestyle.copywriting.common.dto.SaveArticleRequest;
import org.healthylifestyle.copywriting.model.Article;
import org.healthylifestyle.copywriting.model.Category;
import org.healthylifestyle.copywriting.model.Fragment;
import org.healthylifestyle.copywriting.repository.ArticleRepository;
import org.healthylifestyle.copywriting.service.ArticleService;
import org.healthylifestyle.copywriting.service.CategoryService;
import org.healthylifestyle.filesystem.model.Css;
import org.healthylifestyle.filesystem.model.Html;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.filesystem.service.CssService;
import org.healthylifestyle.filesystem.service.HtmlService;
import org.healthylifestyle.filesystem.service.ImageService;
import org.healthylifestyle.filesystem.service.VideoService;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private ImageService imageService;
	@Autowired
	private HtmlService htmlService;
	@Autowired
	private CssService cssService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private UserService userServcie;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private Validator validator;

	private static final int MAX_ARTICLES = 50;
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Override
	public Article findById(Long id) {
		return articleRepository.findById(id).orElse(null);
	}

	@Override
	public Article save(SaveArticleRequest saveRequest) throws ValidationException {
		logger.debug("Start saving article");
		
		BindingResult validationResult = BindingResultFactory.getInstance(saveRequest, "saveArticleRequest", validator);
		validator.validate(saveRequest, validationResult);

		ErrorParser.checkErrors(validationResult, "Exception occurred while saving chat. The request is invalid",
				Type.BAD_REQUEST);

		Category category = categoryService.findById(saveRequest.getCategoryId());
		if (category == null) {
			validationResult.reject("article.save.category.notExist",
					"There is no category with this id: " + saveRequest.getCategoryId());
		}

		List<Fragment> fragments = new ArrayList<>();

		logger.debug("Start fetching fragments and its html, css and media");
		saveRequest.getFragmentRequests().forEach(fragmentRequest -> {
			Fragment fragment = new Fragment();
			fragment.setUuid(fragmentRequest.getUuid());

			Long htmlId = fragmentRequest.getHtmlId();
			Long cssId = fragmentRequest.getCssId();

			Html html = htmlService.findById(htmlId);
			Css css = cssService.findById(cssId);

			fragment.setHtml(html);
			fragment.setCss(css);

			List<Image> images = imageService.findAllByIdIn(fragmentRequest.getImageIds());
			fragment.setImages(images);

			List<Video> videos = videoService.findAllByIdIn(fragmentRequest.getVideoIds());
			fragment.setVideos(videos);

			fragments.add(fragment);
		});

		Image image = imageService.findById(saveRequest.getImageId());

		User user = userServcie
				.findById(Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName()));

		Article article = new Article();
		article.setUuid(saveRequest.getUuid());
		article.setOriginalTitle(saveRequest.getTitle());
		article.setCategory(category);
		article.setFragments(fragments);
		article.setImage(image);
		article.setUser(user);

		article = articleRepository.save(article);
		
		logger.debug("Article has been saved");

		return article;
	}

	@Override
	public List<Article> findAll(int page) {
		return articleRepository.findAll(PageRequest.of(page - 1, MAX_ARTICLES)).getContent();
	}

	@Override
	public List<Article> finAllByTitle(String title, int page) {
		return articleRepository.findAllByTitle(title, PageRequest.of(page - 1, MAX_ARTICLES));
	}

	@Override
	public long count() {
		return articleRepository.count();
	}

	@Override
	public List<Article> findAllByUser(Long userId, int page) {
		return articleRepository.findAllByUser(userId, PageRequest.of(page - 1, MAX_ARTICLES));
	}

	@Override
	public List<Article> findAllByCategory(Long id, int page) {
		return articleRepository.findAllByCategory(id, PageRequest.of(page - 1, MAX_ARTICLES));
	}

}
