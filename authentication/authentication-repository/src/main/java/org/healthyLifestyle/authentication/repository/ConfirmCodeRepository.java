package org.shop.authentication.repository;

import org.shop.authentication.model.ConfirmCode;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmCodeRepository extends CrudRepository<ConfirmCode, Long> {
	ConfirmCode findByUserId(Long id);

	boolean existsByCode(String code);
}
