package org.healthyLifestyle.authentication.repository;

import org.healthyLifestyle.authentication.model.ConfirmCode;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmCodeRepository extends CrudRepository<ConfirmCode, Long> {
	ConfirmCode findByUserId(Long id);

	boolean existsByCode(String code);
}
