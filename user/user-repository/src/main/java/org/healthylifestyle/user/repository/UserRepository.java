package org.healthylifestyle.user.repository;

import java.util.List;

import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

	@Query("select count(*) from user u where u.id in :ids")
	int countAllByIdIn(List<Long> ids);

	@Query("select * from user u where u.id in :ids")
	List<User> findAllByIds(List<Long> ids);
}