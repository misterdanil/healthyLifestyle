package org.shop.authentication.repository;

import org.shop.authentication.model.RefreshToken;
import org.shop.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
	void removeByUser(User user);
}
