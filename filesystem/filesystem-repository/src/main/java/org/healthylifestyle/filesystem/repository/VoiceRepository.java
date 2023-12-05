package org.healthylifestyle.filesystem.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;

public interface VoiceRepository {
	@Query("delete from Voice v inner join Message m on m.voices = v inner join m.chatUser cu where i.id in (:ids) and m = :message and cu.user = :user")
	void deleteByMessage(List<Long> ids, Message message, User user);
}
