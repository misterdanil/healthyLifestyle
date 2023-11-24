package org.healthylifestyle.user.repository.event;

import org.healthylifestyle.user.model.lifestyle.healthy.event.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long> {

}
