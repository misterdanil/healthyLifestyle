package org.healthylifestyle.notification.model;

import java.util.Date;

import org.healthylifestyle.user.model.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification {
	@Id
	@SequenceGenerator(name = "notification_id_generator", sequenceName = "notification_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "notification_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "user_from_id", nullable = false)
	private User from;
	@ManyToOne
	@JoinColumn(name = "user_to_id", nullable = false)
	private User to;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdOn = new Date();

	public Long getId() {
		return id;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
