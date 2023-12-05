package org.healthylifestyle.communication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(name = "reaction_chatUser_message_index", columnList = "chatUser, message", unique = true))
public class Reaction {
	@Id
	@SequenceGenerator(name = "reaction_id_generator", sequenceName = "reaction_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "reaction_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "chat_user_id", nullable = false)
	private ChatUser chatUser;
	@ManyToOne
	@JoinColumn(name = "message_id", nullable = false)
	private Message message;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChatUser getChatUser() {
		return chatUser;
	}

	public void setChatUser(ChatUser chatUser) {
		this.chatUser = chatUser;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

}
