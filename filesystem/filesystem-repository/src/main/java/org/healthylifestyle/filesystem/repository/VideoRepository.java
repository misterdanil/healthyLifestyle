package org.healthylifestyle.filesystem.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {

	@Query("delete from Video v inner join Message m on m.videos = v inner join m.chatUser cu where v.id in (:ids) and m = :message and cu.user = :user")
	void deleteByMessage(List<Long> ids, Message message, User user);
}
