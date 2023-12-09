package org.healthylifestyle.copywriting.service;

import java.util.List;

import org.healthylifestyle.common.error.ValidationException;
import org.healthylifestyle.copywriting.model.mark.Mark;
import org.healthylifestyle.copywriting.model.mark.Type;

public interface MarkService {
	List<Mark> findAllByUser(Long userId);

	Mark save(Long articleId, Type type) throws ValidationException;

	int countByArticleAndType(Long articleId, Type type);
}
