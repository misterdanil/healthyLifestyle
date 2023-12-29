package org.healthylifestyle.communication.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.healthylifestyle.communication.model.settings.Setting;
import org.healthylifestyle.event.model.Event;
import org.healthylifestyle.filesystem.model.Image;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(indexes = @Index(name = "chat_title_index", columnList = "title"))
public class Chat {
	@Id
	@SequenceGenerator(name = "chat_id_generator", sequenceName = "chat_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "chat_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String uuid;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "chat_chatuser", joinColumns = @JoinColumn(name = "chat_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "chatuser_id", nullable = false))
	private List<ChatUser> users;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "chat_message", joinColumns = @JoinColumn(name = "chat_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "message_id", nullable = false))
	private List<Message> messages;
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "setting_id", nullable = false)
	private Setting setting;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id", nullable = false)
	private Image image;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<ChatUser> getUsers() {
		return users;
	}

	public void addUser(ChatUser chatUser) {
		if (users == null) {
			users = new ArrayList<>();
		}

		users.add(chatUser);
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
