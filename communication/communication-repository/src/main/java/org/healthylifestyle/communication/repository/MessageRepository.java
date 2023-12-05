package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
	@Query("select * from Message m where m.chat.id = :chatId and m.value like concat(:text, '%')")
	List<Message> findByTextIn(Long chatId, String text, Pageable pageable);

	@Query("select m from Message m where m.chat.id = :chatId")
	List<Message> findByChat(Long chatId, Pageable pageable);

	@Query("select m from Message m inner join m.favoriteMessages fm inner join fm.chatUser cu inner join User u where u.id = :userId")
	List<Message> findFavorites(Long userId, Pageable pageable);

	@Query("select case when count(m) > 0 then true else false end from Message m where m.id = :messageId and m.user.id = :userId")
	boolean isOwner(Long messageId, Long userId);
}
