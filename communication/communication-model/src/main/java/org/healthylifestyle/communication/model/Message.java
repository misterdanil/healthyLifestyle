package org.healthylifestyle.communication.model;

import java.util.Date;
import java.util.List;

import org.healthylifestyle.filesystem.model.Image;
import org.healthylifestyle.filesystem.model.Video;

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
	@Column(nullable = false)
	private String value;
	@ManyToOne
	@JoinColumn(name = "chatuser_id", nullable = false)
	private ChatUser chatUser;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "message_id", nullable = false)
	private List<Image> images;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "message_id", nullable = false)
	private List<Video> videos;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
