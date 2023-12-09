package org.healthylifestyle.notification.model;

import org.healthylifestyle.communication.model.Message;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "message_notification")
public class MessageNotification extends Notification {
	@ManyToOne
	@JoinColumn(name = "message_id", nullable = false)
	private Message message;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
