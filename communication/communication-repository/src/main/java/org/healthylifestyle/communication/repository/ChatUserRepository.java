package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatUserRepository extends CrudRepository<ChatUser, Long> {

	@Query("select cu from ChatUser cu where cu.chat.id = :chatId and cu.user.id = :userId")
	ChatUser findByChatAndUser(Long chatId, Long userId);

	@Query("select r from Role r " + "inner join ChatUser cu on r.id = element(cu.roles).id "
			+ "where cu.user.id = :userId and cu.chat.id = :chatId")
	List<Role> findRolesByChatIdAndUserId(Long chatId, Long userId);

	@Query("select count(cu) from ChatUser cu where cu.chat.id = :chatId and cu.user.id in (:ids)")
	int countChatUsersByIdIn(Long chatId, List<Long> ids);

	@Query("select count(cu) from ChatUser cu where cu.chat.id = :chatId")
	int count(Long chatId);

	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu where cu.chat.id = :chatId and cu.user.id = :userId")
	boolean existsMember(Long chatId, Long userId);

	@Query("select cu from ChatUser cu where cu.user.id in (:ids)")
	List<ChatUser> findAllByIds(List<Long> ids);

	List<ChatUser> findByChat(Chat chat);

	void deleteByUserAndChat(User user, Chat chat);
	
	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu "
			+ "where cu.chat = :chat and cu.user = :user")
	boolean isMember(Chat chat, User user);

	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu "
			+ "where cu.chat.id = :chatId and cu.user.id = :userId")
	boolean isMember(Long chatId, Long userId);

	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu inner join cu.roles r "
			+ "where cu.chat = :chat and cu.user = :user and r.name = 'ROLE_CHAT_ADMIN'")
	boolean isAdmin(Chat chat, User user);

	@Query("select case when count(cu) > 0 then true else false end from ChatUser cu inner join cu.roles r "
			+ "where cu.chat = :chat and cu.user = :user and r.name = 'ROLE_CHAT_OWNER'")
	boolean isOwner(Chat chat, User user);

}
