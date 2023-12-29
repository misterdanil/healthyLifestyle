package org.healthylifestyle.copywriting.repository;

import java.util.List;

import org.healthylifestyle.copywriting.model.Fragment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FragmentRepository extends CrudRepository<Fragment, Long> {
	@Query("select f from Fragment f inner join Article a on element(a.fragments).id = f.id where a.id = :articleId")
	List<Fragment> findAllByArticle(Long articleId);
}
