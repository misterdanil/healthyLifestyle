package org.healthylifestyle.filesystem.repository;

import org.healthylifestyle.filesystem.model.Css;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CssRepository extends CrudRepository<Css, Long> {

}
