package org.healthylifestyle.copywriting.repository;

import java.util.List;

import org.healthylifestyle.copywriting.model.mark.Mark;
import org.healthylifestyle.copywriting.model.mark.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarkRepository extends JpaRepository<Mark, Long> {
	@Query("select m from Mark m where m.article.id = :articleId and m.user.id = :userId")
	Mark findByArticleAndUser(Long articleId, Long userId);

	@Query("select m from Mark m where m.user.id = :userId")
	List<Mark> findAllByUser(Long userId);

	@Query("select count(m) from Mark m where m.article.id = :articleId and m.type = :type")
	int countByArticleAndType(Long articleId, Type type);

}
