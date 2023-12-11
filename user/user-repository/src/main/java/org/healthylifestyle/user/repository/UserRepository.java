package org.healthylifestyle.user.repository;

import java.util.List;

import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

	User findByEmail(String email);

	@Query("select count(u) from User u where u.id in (:ids)")
	int countAllByIdIn(List<Long> ids);

	@Query("select u from User u where u.id in (:ids)")
	List<User> findAllByIds(List<Long> ids);

	User findByResourceIdAndResourceName(String oauth2Id, String oauth2Resource);

	boolean existsByEmail(String email);

	boolean existsByResourceIdAndResourceName(String oauth2Id, String oauth2Resource);
}
