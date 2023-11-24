package org.healthylifestyle.filesystem.repository;

import org.healthylifestyle.filesystem.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {
	
}
