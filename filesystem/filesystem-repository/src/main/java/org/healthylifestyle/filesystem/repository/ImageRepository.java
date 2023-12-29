package org.healthylifestyle.filesystem.repository;

import java.util.List;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
	@Query("select i from Image i where i.id in (:ids)")
	List<Image> findAllById(List<Long> ids);

	@Query("delete from Image i where i.id in (:ids) and i.id in (select i.id from Image i inner join Message m on element(m.images).id = i.id where m = :message and m.chatUser.user = :user)")
	void deleteByMessage(List<Long> ids, Message message, User user);

	@Query("select i from Image i inner join Fragment f on element(f.images).id = i.id where f.id = :fragmentId")
	List<Image> findByFragment(Long fragmentId);

	@Query("select i from Image i inner join Article a on a.image.id = i.id where a.id = :id")
	Image findByArticle(Long id);

	@Query("select i from Image i inner join Message m on element(m.images).id = i.id where m = :message and m.chatUser.user = :user and i.id in (:ids)")
	List<Image> findByMessage(List<Long> ids, Message message, User user);

	@Query("select i from Image i inner join Message m on element(m.images).id = i.id where m.id = :id")
	List<Image> findByMessage(Long id);

}
