package org.healthylifestyle.communication.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.model.Video;
import org.healthylifestyle.filesystem.model.Voice;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table
public class Message {
	@Id
	@SequenceGenerator(name = "message_id_generator", sequenceName = "message_sequence")
	@GeneratedValue(generator = "message_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	private String uuid;
	@Column(nullable = false)
	private String value;
	@ManyToOne
	@JoinColumn(name = "chatuser_id", nullable = false)
	private ChatUser chatUser;
	@ManyToOne
	@JoinColumn(name = "chat_id", nullable = false)
	private Chat chat;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "message_id")
	private List<Image> images;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "message_id", nullable = false)
	private List<Video> videos;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "message_id", nullable = false)
	private List<Voice> voices;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Reaction> reactions;
	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message answeredMessage;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ChatUser getChatUser() {
		return chatUser;
	}

	public void setChatUser(ChatUser chatUser) {
		this.chatUser = chatUser;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public List<Image> getImages() {
		return images;
	}

	public void addImages(List<Image> images) {
		if (images == null) {
			return;
		}

		if (this.images == null) {
			this.images = new ArrayList<>();
		}

		this.images.addAll(images);
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void addVideos(List<Video> videos) {
		if (videos == null) {
			return;
		}

		if (this.videos == null) {
			this.videos = new ArrayList<>();
		}

		this.videos.addAll(videos);

	}

	public List<Voice> getVoices() {
		return voices;
	}

	public void addVoices(List<Voice> voices) {
		if (voices == null) {
			return;
		}
		if (this.voices == null) {
			this.voices = new ArrayList<>();
		}

		this.voices.addAll(voices);
	}

	public List<Reaction> getReactions() {
		return reactions;
	}

	public void setReactions(List<Reaction> reactions) {
		this.reactions = reactions;
	}

	public Message getAnsweredMessage() {
		return answeredMessage;
	}

	public void setAnsweredMessage(Message answeredMessage) {
		this.answeredMessage = answeredMessage;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
