package org.healthylifestyle.copywriting.service.impl;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.copywriting.model.Article;
import org.healthylifestyle.copywriting.model.mark.Mark;
import org.healthylifestyle.copywriting.model.mark.Type;
import org.healthylifestyle.copywriting.repository.MarkRepository;
import org.healthylifestyle.copywriting.service.ArticleService;
import org.healthylifestyle.copywriting.service.MarkService;
import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MarkServiceImpl implements MarkService {
	@Autowired
	private MarkRepository markRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleService articleService;

	@Override
	public Mark save(Long articleId, Type type) throws ValidationException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());

		Article article = articleService.findById(articleId);

		if (article == null) {
			throw new ValidationException(null, null, org.healthylifestyle.common.error.Type.NOT_FOUND);
		}

		Mark mark = markRepository.findByArticleAndUser(articleId, user.getId());
		if (mark == null) {
			mark = new Mark();

			mark.setType(type);
			mark.setUser(user);
			mark.setArticle(article);
		} else {
			mark.setType(type);
		}

		mark = markRepository.save(mark);

		return mark;
	}

	@Override
	public List<Mark> findAllByUser(Long userId) {
		return markRepository.findAllByUser(userId);
	}

	@Override
	public int countByArticleAndType(Long articleId, Type type) {
		return markRepository.countByArticleAndType(articleId, type);
	}

}
