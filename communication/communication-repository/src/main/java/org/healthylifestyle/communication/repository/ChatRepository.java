package org.healthylifestyle.communication.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {
	@Query("select * from chat c inner join setting s on c.id = s.chat_id "
			+ "where title like :title% and s.privacy = OPENED")
	List<Chat> findByTitle(String title);
}
