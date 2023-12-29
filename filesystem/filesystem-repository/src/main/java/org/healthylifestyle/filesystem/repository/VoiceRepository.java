package org.healthylifestyle.filesystem.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Voice;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoiceRepository extends CrudRepository<Voice, Long> {
	@Query("select v from Voice v where v.id in (:ids)")
	List<Voice> findAllByIdIn(List<Long> ids);

	@Query("delete from Voice v where v.id in (:ids) and v.id in (select v.id from Voice v inner join Message m on element(m.voices).id = v.id where m = :message and m.chatUser.user = :user)")
	void deleteByMessage(List<Long> ids, Message message, User user);
}
