package org.healthylifestyle.notification.model;

import org.healthylifestyle.communication.model.Message;
import org.healthylifestyle.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "write_notification")
public class WriteNotification extends Notification {
	@ManyToOne
	@JoinColumn(name = "user_to_id", nullable = false)
	private User to;
	@ManyToOne
	@JoinColumn(name = "message_id", nullable = false)
	private Message message;

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
