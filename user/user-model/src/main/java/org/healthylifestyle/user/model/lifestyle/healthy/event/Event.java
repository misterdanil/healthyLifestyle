package org.healthylifestyle.user.model.lifestyle.healthy.event;

import java.util.Date;
import java.util.List;

import org.healthylifestyle.user.model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table
public class Event {
	@Id
	@SequenceGenerator(name = "event_id_generator", sequenceName = "event_sequence", initialValue = 1, allocationSize = 20)
	@GeneratedValue(generator = "event_id_generator", strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(nullable = false)
	private String description;
	private String place;
	@ManyToMany
	@JoinTable(name = "event_user", joinColumns = @JoinColumn(name = "event_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false))
	private List<User> users;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
	private List<Completeness> completenesses;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<Completeness> getCompletenesses() {
		return completenesses;
	}

	public void setCompletenesses(List<Completeness> completenesses) {
		this.completenesses = completenesses;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
