package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
	@Query("select c from Chat c inner join c.setting s where c.title like concat(:title, '%') and s.privacy = OPENED")
	List<Chat> findByTitle(String title);

	@Query("select c from Chat c inner join Message m on m.chat.id = c.id where m.id = :messageId")
	Chat findByMessage(Long messageId);

	@Query("select c from Chat c inner join c.users.user u where u.id = :userId")
	List<Chat> findByUser(Long userId, Pageable pageable);
}
