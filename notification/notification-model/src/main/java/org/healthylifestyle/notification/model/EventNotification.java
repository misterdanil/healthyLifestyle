package org.healthylifestyle.notification.model;

import org.healthylifestyle.user.model.User;
import org.healthylifestyle.user.model.lifestyle.healthy.event.Event;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_notification")
public class EventNotification extends Notification {
	@ManyToOne
	@JoinColumn(name = "user_from_id", nullable = false)
	private User from;
	@ManyToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
