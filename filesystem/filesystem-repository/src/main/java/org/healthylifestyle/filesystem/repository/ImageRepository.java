package org.healthylifestyle.filesystem.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
	@Query("delete from Image i inner join Message m on m.images = i inner join m.chatUser cu where i.id in (:ids) and m = :message and cu.user = :user")
	void deleteByMessage(List<Long> ids, Message message, User user);
}
