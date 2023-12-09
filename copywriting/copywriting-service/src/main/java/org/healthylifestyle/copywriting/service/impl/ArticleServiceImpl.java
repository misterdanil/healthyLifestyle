package org.healthylifestyle.copywriting.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.copywriting.common.dto.SaveArticleRequest;
import org.healthylifestyle.copywriting.model.Article;
import org.healthylifestyle.copywriting.model.Fragment;
import org.healthylifestyle.copywriting.repository.ArticleRepository;
import org.healthylifestyle.copywriting.service.ArticleService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:fragment_files_path.properties")
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

	private static final int MAX_ARTICLES = 50;

	@Override
	public Article findById(Long id) {
		return articleRepository.findById(id).orElse(null);
	}

	@Override
	public Article save(SaveArticleRequest saveRequest) {
		List<Fragment> fragments = new ArrayList<>();

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
		});

		Image image = imageService.findById(saveRequest.getImageId());

		User user = userServcie.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		Article article = new Article();
		article.setUuid(saveRequest.getUuid());
		article.setOriginalTitle(saveRequest.getTitle());
		article.setFragments(fragments);
		article.setImage(image);
		article.setUser(user);

		article = articleRepository.save(article);

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

}
