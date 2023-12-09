package org.healthyLifestyle.authentication.repository;

import org.healthyLifestyle.authentication.model.RefreshToken;
import org.healthylifestyle.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
	void removeByUser(User user);
}
