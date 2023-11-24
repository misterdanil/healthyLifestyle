package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
	@Query("select * from chat c inner join setting s on c.id = s.chat_id "
			+ "where title like :title% and s.privacy = OPENED")
	List<Chat> findByTitle(String title);

	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu "
			+ "where cu.chat = :chat and cu.user = :user")
	boolean isMember(Chat chat, User user);
	
	@Query("select case when count(r) > 0 then true else false end from Role r "
			+ "inner join chatuser_role cur on r.id = cur.role_id "
			+ "inner join ChatUser cu on cu.id = cur.chatuser_id "
			+ "where cu.chat = :chat and cu.user = :user and r.name = 'ROLE_CHAT_ADMIN'")boolean isAdmin(Chat chat, User user);

	@Query("select case when count(r) > 0 then true else false end from Role r "
			+ "inner join chatuser_role cur on r.id = cur.role_id "
			+ "inner join ChatUser cu on cu.id = cur.chatuser_id "
			+ "where cu.chat = :chat and cu.user = :user and r.name = 'ROLE_CHAT_OWNER'")
	boolean isOwner(Chat chat, User user);
}
