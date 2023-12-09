package org.healthylifestyle.notification.model;

import org.healthylifestyle.event.model.Event;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_notification")
public class EventNotification extends Notification {
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
