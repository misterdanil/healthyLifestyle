package org.healthylifestyle.event.repository;

import java.util.List;

import org.healthylifestyle.event.model.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {
	@Query("select e from Event e inner join e.users u where u.id = :userId")
	List<Event> findAllByUser(Long userId, Pageable pageable);

	List<Event> findAll(Pageable pageable);

	@Query("select case when count(e) > 0 then true else false end from Event e inner join e.users u where u.id = :userId and e.id = :eventId")
	boolean isMember(Long eventId, Long userId);
}
