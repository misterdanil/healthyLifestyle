package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends CrudRepository<ChatUser, Long> {

	@Query("select * from ChatUser cu where cu.chat_id = :chatId and cu.user_id = :user_id")
	ChatUser findByChatAndUser(Long chatId, Long userId);

	@Query("select * from Role r " + "inner join chatuser_roles cur on r.id = cur.role_id "
			+ "inner join chat_user cu on cu.id = cur.chatuser_id "
			+ "where cu.user_id = :userId and cu.chat_id = :chatId")
	List<Role> findRolesByChatIdAndUserId(Long chatId, Long userId);

	@Query("select count(cu) from chatUser cu where cu.chat_id = :chatId and cu.id in :ids")
	int countChatUsersByIdIn(Long chatId, List<Long> ids);

	@Query("select count(cu) from ChatUser cu where cu.chat_id = :chatId")
	int count(Long chatId);

	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu where cu.chat_id = :chatId and cu.user_id = :userId")
	boolean existsMember(Long chatId, Long userId);

	@Query("select cu from chatUser cu where cu.id in :ids")
	List<ChatUser> findAllByIds(List<Long> ids);

	List<ChatUser> findByChat(Chat chat);

	void deleteByUserAndChat(User user, Chat chat);

}
