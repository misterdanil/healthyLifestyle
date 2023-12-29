package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
	@Query("select m from Message m where m.chat.id = :chatId and m.value like concat(:text, '%')")
	List<Message> findByTextIn(Long chatId, String text, Pageable pageable);

	@Query("select m from Message m where m.chat.id = :chatId")
	List<Message> findByChat(Long chatId, Pageable pageable);

	@Query("select fm from Message m inner join m.chatUser cu inner join cu.favoriteMessages fm where cu.user.id = :userId")
	List<Message> findFavorites(Long userId, Pageable pageable);

	@Query("select case when count(m) > 0 then true else false end from Message m where m.id = :messageId and m.chatUser.user.id = :userId")
	boolean isOwner(Long messageId, Long userId);

	@Query("select m from Message m order by m.createdOn desc limit 1")
	Message findLastByChat(Long chatId);
}
