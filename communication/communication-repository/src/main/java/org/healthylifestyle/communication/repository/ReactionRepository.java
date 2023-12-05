package org.healthylifestyle.communication.repository;

import org.healthylifestyle.communication.model.ChatUser;
import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.communication.model.Reaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends CrudRepository<Reaction, Long> {
	@Query("select case when count(r) > 0 then true else false end from Reaction r where r.chatUser = :chatUser and r.message = :message")
	boolean existsByUser(ChatUser chatUser, Message message);
}
