package org.healthylifestyle.communication.model;

import java.util.ArrayList;
import java.util.List;

import org.healthylifestyle.user.model.Role;
import org.healthylifestyle.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "chat_user")
public class ChatUser {
	@Id
	@SequenceGenerator(name = "chatuser_id_generator", sequenceName = "chatuser_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "chatuser_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "chatUser")
	private List<Message> messages;
	@ManyToMany
	@JoinTable(name = "chatuser_role", joinColumns = @JoinColumn(name = "chatuser_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
	private List<Role> roles;
	@ManyToMany
	@JoinTable(name = "favorite_messages", joinColumns = @JoinColumn(name = "chatuser_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "message_id", nullable = false))
	private List<Message> favoriteMessages;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "chatUser")
	private List<Reaction> reactions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void addRoles(List<Role> roles) {
		if (roles != null) {
			roles.forEach(role -> {
				addRole(role);
			});
		}
	}

	public void addRole(Role role) {
		if (roles == null) {
			roles = new ArrayList<>();
		}

		roles.add(role);
	}

	public List<Message> getFavoriteMessages() {
		return favoriteMessages;
	}

	public void addFavoriteMessages(List<Message> favoriteMessages) {
		if (favoriteMessages == null || favoriteMessages.isEmpty()) {
			return;
		}

		checkFavoriteMessages();

		this.favoriteMessages.addAll(favoriteMessages);
	}

	public void addFavoriteMessage(Message message) {
		if (message == null) {
			return;
		}

		checkFavoriteMessages();

		this.favoriteMessages.add(message);
	}

	private void checkFavoriteMessages() {
		if (this.favoriteMessages == null) {
			this.favoriteMessages = new ArrayList<>();
		}
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void addReaction(Reaction reaction) {
		if (reaction == null) {
			return;
		}

		if (reactions == null) {
			reactions = new ArrayList<>();
		}

		reactions.add(reaction);
	}

}
