package org.healthylifestyle.copywriting.repository;

import java.util.List;

import org.healthylifestyle.copywriting.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {
	@Query("select a from Article a inner join a.user u where u.id = :userId")
	List<Article> findAllByUser(Long userId, Pageable pageable);

	@Query("select a from Article a where a.originalTitle like concat(:title, '%')")
	List<Article> findAllByTitle(String title, Pageable pageable);

	@Query("select a from Article a where a.category.id = :id")
	List<Article> findAllByCategory(Long id, Pageable pageable);
}
