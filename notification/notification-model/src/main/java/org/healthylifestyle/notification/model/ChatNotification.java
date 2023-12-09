package org.healthylifestyle.notification.model;

import org.healthylifestyle.communication.model.Chat;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_notification")
public class ChatNotification extends Notification {
	@ManyToOne
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

}
