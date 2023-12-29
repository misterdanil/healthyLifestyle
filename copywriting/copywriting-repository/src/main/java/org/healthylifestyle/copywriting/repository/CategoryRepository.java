package org.healthylifestyle.copywriting.repository;

import org.healthylifestyle.copywriting.model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
