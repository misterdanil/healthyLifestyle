package org.healthylifestyle.event.repository;

import org.healthylifestyle.event.model.Completeness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompletenessRepository extends JpaRepository<Completeness, Long> {
	@Query("select c from Completeness c where c.event.id = :eventId and c.user.id = :userId")
	Completeness findByEventAndUser(Long eventId, Long userId);
}
