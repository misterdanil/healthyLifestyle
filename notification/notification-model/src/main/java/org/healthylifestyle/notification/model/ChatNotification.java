package org.healthylifestyle.notification.model;

import java.util.List;

import org.healthylifestyle.communication.model.Chat;
import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_notification")
public class ChatNotification extends Notification {
	@ManyToOne
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;
	@ManyToOne
	@JoinColumn(name = "user_from_id", nullable = false)
	private User from;
	@ManyToOne
	@JoinColumn(name = "user_to_id", nullable = false)
	private User to;
	@ManyToMany
	@JoinTable(name = "chatnotification_role", joinColumns = @JoinColumn(name = "chat_notification_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
	private List<Role> roles;

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

}
