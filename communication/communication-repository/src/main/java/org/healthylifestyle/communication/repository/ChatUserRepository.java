package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.user.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends CrudRepository<ChatUser, Long> {

	@Query("select * from role r " + "inner join chatuser_roles cur on r.id = cur.role_id "
			+ "inner join user u on u.user_id = cur.user_id " + "where u.username = :username")
	List<Role> findRolesByUsername(String username);

	@Query("select count(*) from chatUser cu inner join chat c on cu.chat_id = c.id "
			+ "where c.id = :chatId and cu.id in :ids")
	int countChatUsersByIdIn(Long chatId, List<Long> ids);

	@Query("select * from chatUser cu where cu.id in :ids")
	List<ChatUser> findAllByIds(List<Long> ids);

}
