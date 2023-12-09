package org.healthylifestyle.filesystem.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

	@Query("select v from Video v where v.id in (:ids)")
	List<Video> findAllByIdIn(List<Long> ids);

	@Query("delete from Video v inner join Message m on m.videos = v inner join m.chatUser cu where v.id in (:ids) and m = :message and cu.user = :user")
	void deleteByMessage(List<Long> ids, Message message, User user);
}
